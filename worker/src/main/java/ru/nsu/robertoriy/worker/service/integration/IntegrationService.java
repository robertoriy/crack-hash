package ru.nsu.robertoriy.worker.service.integration;

import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;

public interface IntegrationService {
    void sendDataToManager(WorkerResponse workerResponse);
}
