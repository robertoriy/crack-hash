package ru.nsu.robertoriy.manager.service.storage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;
import ru.nsu.robertoriy.manager.dao.CrackingTaskRepository;
import ru.nsu.robertoriy.manager.dao.entity.CrackingTask;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;
import ru.nsu.robertoriy.manager.model.CrackStatus;

@Slf4j
@Service
@Profile("distributed")
public class PersistentStorageService implements StorageService {
    private final CrackingTaskRepository taskRepository;
    private final int partCount;

    public PersistentStorageService(ApplicationConfig applicationConfig, CrackingTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        partCount = applicationConfig.partCount();
    }

    @Override
    public UUID createRequest() {
        UUID requestId = UUID.randomUUID();

        CrackingTask task = CrackingTask.builder()
            .id(requestId)
            .status(CrackStatus.IN_PROGRESS)
            .updatedCount(0)
            .build();

        taskRepository.save(task);

        return requestId;
    }

    @Override
    public StatusResponse getStatusResponse(UUID requestId) {
        CrackingTask task = taskRepository.findById(requestId)
            .orElseThrow(NoSuchElementException::new);

        return new StatusResponse(task.getStatus(), task.getData());
    }

    @Transactional
    @Override
    public void updateRequest(UUID requestId, List<String> data) {
        log.info("Updating request id {} with data - {}", requestId, data);
        CrackingTask task = taskRepository.findById(requestId)
            .orElseThrow(IllegalStateException::new);
        checkCorrectState(task);

        if (task.getData() == null) {
            task.setData(data);
        } else {
            task.getData().addAll(data);
        }
        task.setUpdatedCount(task.getUpdatedCount() + 1);
        log.info("Number of updates for request {} is {}", requestId, task.getUpdatedCount());

        if (task.getUpdatedCount() == partCount) {
            log.info("Received data from all workers for request {}, new status - READY", requestId);
            task.setStatus(CrackStatus.READY);
        }
        taskRepository.save(task);
    }

    @Transactional
    @Override
    public void updateStatusOnTimeout(UUID requestId) {
        log.info("Timeout for request {}", requestId);
        if (!isReady(requestId)) {
            markAsError(requestId);
        }
    }

    private void checkCorrectState(CrackingTask task) {
        if (task.getStatus() == CrackStatus.ERROR || task.getStatus() == CrackStatus.READY) {
            throw new IllegalStateException();
        }
    }

    private boolean isReady(UUID requestId) {
        CrackingTask task = taskRepository.findById(requestId)
            .orElseThrow(IllegalStateException::new);

        Objects.requireNonNull(task.getStatus());

        return task.getStatus() == CrackStatus.READY;
    }

    private void markAsError(UUID requestId) {
        log.info("Marking request {} as error", requestId);
        CrackingTask task = taskRepository.findById(requestId)
            .orElseThrow(IllegalStateException::new);

        task.setData(null);
        task.setStatus(CrackStatus.ERROR);
        task.setUpdatedCount(-1);

        taskRepository.save(task);
    }
}
