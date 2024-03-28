package ru.nsu.robertoriy.manager.service.integration;

import ru.nsu.robertoriy.manager.dto.request.TaskRequest;

public interface IntegrationService {
    void sendTaskToWorkers(TaskRequest taskRequest);
}
