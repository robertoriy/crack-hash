package ru.nsu.robertoriy.worker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.exception.HashCrackingException;
import ru.nsu.robertoriy.worker.service.worker.WorkerService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/worker")
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping("/hash/crack/task")
    public ResponseEntity<HttpStatus> crackHash(
        @RequestBody TaskRequest taskRequest
    ) {
        log.info("Received: task - {}", taskRequest);
        try {
            workerService.completeTask(taskRequest);
            return ResponseEntity.ok().build();
        } catch (HashCrackingException exception) {
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
