package ru.nsu.robertoriy.worker.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PatchExchange;
import reactor.core.publisher.Mono;
import ru.nsu.robertoriy.worker.dto.request.WorkerRequest;

public interface ManagerClient {
    @PatchExchange("/internal/api/manager/hash/crack/request")
    Mono<ResponseEntity<Void>> sendData(@RequestBody WorkerRequest workerRequest);
}
