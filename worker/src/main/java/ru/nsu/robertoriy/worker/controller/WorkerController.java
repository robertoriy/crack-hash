package ru.nsu.robertoriy.worker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;
import ru.nsu.robertoriy.worker.mapper.TaskMapper;
import ru.nsu.robertoriy.worker.service.worker.WorkerService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/worker")
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping("/hash/crack/task")
    public void crackHash(
        @RequestBody TaskRequest taskRequest
    ) {
        try {
            log.info("{}", TaskMapper.INSTANCE.taskRequestToTask(taskRequest));
            workerService.completeTask(taskRequest);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

//    @PostMapping("/hash/crack/task")
//    public ResponseEntity<WorkerResponse> crackHash(
//        @RequestBody TaskRequest taskRequest
//    ) {
//        try {
//            log.info("{}", TaskMapper.INSTANCE.taskRequestToTask(taskRequest));
//            var response = workerService.completeTask(taskRequest);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
