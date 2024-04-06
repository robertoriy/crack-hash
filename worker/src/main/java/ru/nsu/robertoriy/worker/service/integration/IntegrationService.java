package ru.nsu.robertoriy.worker.service.integration;

import ru.nsu.robertoriy.worker.dto.request.WorkerRequest;

public interface IntegrationService {
    void sendDataToManager(WorkerRequest workerRequest);
}
