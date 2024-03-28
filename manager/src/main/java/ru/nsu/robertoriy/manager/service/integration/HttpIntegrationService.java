package ru.nsu.robertoriy.manager.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.manager.client.WorkerClient;
import ru.nsu.robertoriy.manager.dto.request.TaskRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class HttpIntegrationService implements IntegrationService {
    private final WorkerClient workerClient;

    @Override
    public void sendTaskToWorkers(TaskRequest taskRequest) {
        log.info("sending Data To Worker, task - {}", taskRequest);

        ResponseEntity<Void> result = workerClient.sendTask(taskRequest);

        log.info("sent");
        log.info("status code: {}", result.getStatusCode());
    }
}
