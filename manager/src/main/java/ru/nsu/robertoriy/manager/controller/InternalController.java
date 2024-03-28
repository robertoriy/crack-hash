package ru.nsu.robertoriy.manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.service.ManagerService;
import ru.nsu.robertoriy.manager.exception.NoSuchRequestException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/manager")
public class InternalController {
    private final ManagerService managerService;

    @PatchMapping("/hash/crack/request")
    public void update(
        @RequestBody WorkerRequest workerRequest
    ) {
        try {
            log.info("Update hash request - {}", workerRequest);
            managerService.update(workerRequest);
        } catch (NoSuchRequestException exception) {
//            return ResponseEntity.notFound().build();
            log.error("Error no such request- {}", exception.getMessage());
        } catch (Exception exception) {
            log.error("Error update hash request - {}", exception.getMessage());
//            return ResponseEntity.internalServerError().build();
        }
    }
}
