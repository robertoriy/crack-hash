package ru.nsu.robertoriy.worker.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.client.ManagerClient;
import ru.nsu.robertoriy.worker.dto.request.WorkerRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultIntegrationService implements IntegrationService {
    private final ManagerClient managerClient;

    @Override
    public void sendDataToManager(WorkerRequest workerRequest) {
        log.info(
            "Sending data to manager, requestId - {}, data - {}",
            workerRequest.requestId(),
            workerRequest.data()
        );

        managerClient.sendData(workerRequest)
            .subscribe(response -> log.info("Response from manager. Status code: {}", response.getStatusCode()));

        log.info("Sent Data To Manager");
    }
}
