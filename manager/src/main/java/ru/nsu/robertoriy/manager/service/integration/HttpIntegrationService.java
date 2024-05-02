package ru.nsu.robertoriy.manager.service.integration.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.manager.client.WorkerClient;
import ru.nsu.robertoriy.manager.dto.request.TaskRequest;
import ru.nsu.robertoriy.manager.service.integration.IntegrationService;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile("simple")
public class HttpIntegrationService implements IntegrationService {
    private final WorkerClient workerClient;

    @Override
    public void sendTaskToWorkers(TaskRequest taskRequest) {
        log.info("Sending data to worker, task - {}", taskRequest);

        workerClient.sendTask(taskRequest)
            .subscribe(response -> log.info("Response from worker. Status code: {}", response.getStatusCode()));

        log.info("Sent data to worker");
    }
}
