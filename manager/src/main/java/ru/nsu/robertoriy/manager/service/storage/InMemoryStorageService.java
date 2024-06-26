package ru.nsu.robertoriy.manager.service.storage;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;
import ru.nsu.robertoriy.manager.model.CrackStatus;
import ru.nsu.robertoriy.manager.dao.RequestRepository;

@Slf4j
@Service
@Profile("simple")
public class InMemoryStorageService implements StorageService {
    private final RequestRepository requestRepository;
    private final Map<UUID, Integer> updateCounter;
    private final int partCount;

    public InMemoryStorageService(
        ApplicationConfig applicationConfig,
        RequestRepository requestRepository,
        Map<UUID, Integer> updateCounter
    ) {
        partCount = applicationConfig.partCount();
        this.requestRepository = requestRepository;
        this.updateCounter = updateCounter;
    }

    @Override
    public UUID createRequest() {
        StatusResponse statusToSave = new StatusResponse(CrackStatus.IN_PROGRESS, null);
        UUID requestId = requestRepository.save(statusToSave);
        updateCounter.put(requestId, 0);
        return requestId;
    }

    @Override
    public StatusResponse getStatusResponse(UUID requestId) {
        return requestRepository.get(requestId);
    }

    @Override
    public synchronized void updateRequest(UUID requestId, List<String> data) {
        checkExistence(requestId);
        checkCorrectState(requestId);

        addDataToRequest(requestId, data);
    }

    @Override
    public void updateStatusOnTimeout(UUID requestId) {
        log.info("Timeout for request {}", requestId);
        if (!isReady(requestId)) {
            markAsError(requestId);
        }
    }

    private void checkExistence(UUID requestId) {
        if (!updateCounter.containsKey(requestId)) {
            throw new IllegalStateException();
        }
    }

    private void checkCorrectState(UUID requestId) {
        CrackStatus currentStatus = requestRepository.get(requestId).status();

        if (currentStatus == CrackStatus.ERROR || currentStatus == CrackStatus.READY) {
            throw new IllegalStateException();
        }

        if (updateCounter.get(requestId) == partCount) {
            throw new IllegalStateException();
        }
    }

    private void addDataToRequest(UUID requestId, List<String> data) {
        updateCounter.computeIfPresent(requestId, (k, v) -> v + 1);

        List<String> newData = requestRepository.get(requestId).data();
        if (newData == null) {
            newData = data;
        } else {
            newData.addAll(data);
        }

        CrackStatus newStatus = requestRepository.get(requestId).status();

        if (updateCounter.get(requestId) == partCount) {
            newStatus = CrackStatus.READY;
        }

        StatusResponse statusToUpdate = new StatusResponse(newStatus, newData);

        requestRepository.update(requestId, statusToUpdate);
    }

    private boolean isReady(UUID requestId) {
        return requestRepository.get(requestId).status() == CrackStatus.READY;
    }

    private void markAsError(UUID requestId) {
        log.info("Marking request {} as error", requestId);

        requestRepository.update(requestId, new StatusResponse(CrackStatus.ERROR, null));
    }
}
