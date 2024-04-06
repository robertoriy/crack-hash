package ru.nsu.robertoriy.manager.service.manager;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;
import ru.nsu.robertoriy.manager.dto.request.CrackRequest;
import ru.nsu.robertoriy.manager.dto.request.TaskRequest;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.dto.response.CrackResponse;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;
import ru.nsu.robertoriy.manager.exception.NoSuchRequestException;
import ru.nsu.robertoriy.manager.exception.ServiceException;
import ru.nsu.robertoriy.manager.service.integration.IntegrationService;
import ru.nsu.robertoriy.manager.service.storage.StorageService;

@Slf4j
@Service
public class DefaultManagerService implements ManagerService {
    private final IntegrationService integrationService;
    private final StorageService storageService;
    private final ScheduledExecutorService scheduler;
    private final String alphabet;
    private final int partCount;
    private final long timeout;

    public DefaultManagerService(
        ApplicationConfig applicationConfig,
        StorageService storageService,
        IntegrationService integrationService,
        ScheduledExecutorService scheduler
    ) {
        alphabet = applicationConfig.alphabet();
        partCount = applicationConfig.partCount();
        timeout = applicationConfig.timeout();
        this.integrationService = integrationService;
        this.storageService = storageService;
        this.scheduler = scheduler;
    }

    @Override
    public CrackResponse crack(CrackRequest crackRequest) {
        log.info("Cracking hash - {} - with max length - {}", crackRequest.hash(), crackRequest.maxLength());

        UUID requestId = storageService.createRequest();
        log.info("Generated RequestId - {}", requestId);

        sendTaskToWorkers(requestId, crackRequest);

        setTimeoutToScheduler(requestId);

        return new CrackResponse(requestId);
    }

    @Override
    public StatusResponse status(UUID requestId) {
        log.info("Returning status for reqest - {}", requestId);

        try {
            return storageService.getStatusResponse(requestId);
        } catch (NoSuchElementException exception) {
            log.info("Getting status error: Repository doesn't contain the request");
            throw new NoSuchRequestException();
        }
    }

    @Override
    public void update(WorkerRequest workerRequest) {
        log.info("Updating hash");
        log.info("requestId - {}, data - {}", workerRequest.requestId(), workerRequest.data());

        try {
            storageService.updateRequest(workerRequest.requestId(), workerRequest.data());
        } catch (NoSuchElementException exception) {
            log.error("Updating error: Repository doesn't contain the request");
            throw new NoSuchRequestException();
        } catch (IllegalStateException exception) {
            log.error("Updating status error: Illegal state for that operation");
            throw new ServiceException("Illegal operation");
        }
    }

    private void sendTaskToWorkers(UUID requestId, CrackRequest crackRequest) {
        for (int i = 0; i < partCount; i++) {
            TaskRequest taskRequest = new TaskRequest(
                requestId,
                crackRequest.hash(),
                alphabet,
                crackRequest.maxLength(),
                partCount,
                i
            );
            log.info("Generated task to worker - {}", i);
            integrationService.sendTaskToWorkers(taskRequest);
        }
    }

    private void setTimeoutToScheduler(UUID requestId) {
        log.info("Setting timeout to scheduler for request - {}", requestId);

        scheduler.schedule(
            () -> storageService.updateStatusOnTimeout(requestId),
            timeout,
            TimeUnit.SECONDS
        );
    }
}
