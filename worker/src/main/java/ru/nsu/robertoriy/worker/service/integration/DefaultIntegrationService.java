package ru.nsu.robertoriy.worker.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.client.ManagerClient;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultIntegrationService implements IntegrationService {
    private final ManagerClient managerClient;

    @Override
    public void sendDataToManager(WorkerResponse workerResponse) {
        log.info(
            "sending Data To Manager, requestId - {}, data - {}",
            workerResponse.requestId(),
            workerResponse.data()
        );

        ResponseEntity<Void> result = managerClient.sendData(workerResponse);

        log.info("sent");
        log.info("status code: {}", result.getStatusCode());
    }
}
