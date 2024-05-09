package ru.nsu.robertoriy.worker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
@Profile("simple")
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping("/hash/crack/task")
    public ResponseEntity<HttpStatus> crackHash(
        @RequestBody TaskRequest taskRequest
    ) {
        log.info("Received: task - {}", taskRequest);
        try {
            List<String> data = workerService.completeTask(taskRequest);
            workerService.sendTask(taskRequest.requestId(), data);
            return ResponseEntity.ok().build();
        } catch (HashCrackingException exception) {
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error("{} {}", exception, exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
