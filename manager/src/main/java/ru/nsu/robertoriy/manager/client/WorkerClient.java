package ru.nsu.robertoriy.manager.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ru.nsu.robertoriy.manager.dto.request.TaskRequest;

public interface WorkerClient {
    @PostExchange("/internal/api/worker/hash/crack/task")
    ResponseEntity<Void> sendTask(@RequestBody TaskRequest taskRequest);
}
