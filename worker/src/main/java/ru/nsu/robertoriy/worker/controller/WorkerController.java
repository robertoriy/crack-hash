package ru.nsu.robertoriy.worker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.worker.dto.request.ManagerRequest;
import ru.nsu.robertoriy.worker.service.HashCrackingService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/worker")
public class WorkerController {
    private final HashCrackingService hashCrackingService;

    @PostMapping("/hash/crack/task")
    public void crackHash(
        @RequestBody ManagerRequest managerRequest
    ) {
        hashCrackingService.completeTask(managerRequest);
    }
}
