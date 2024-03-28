package ru.nsu.robertoriy.worker.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PatchExchange;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;

public interface ManagerClient {
    @PatchExchange("/internal/api/manager/hash/crack/request")
    ResponseEntity<Void> sendData(@RequestBody WorkerResponse workerResponse);
}
