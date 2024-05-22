package ru.nsu.robertoriy.manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.exception.NoSuchRequestException;
import ru.nsu.robertoriy.manager.exception.ServiceException;
import ru.nsu.robertoriy.manager.service.manager.ManagerService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/api/manager")
@Profile("simple")
public class InternalController {
    private final ManagerService managerService;

    @PatchMapping("/hash/crack/request")
    public ResponseEntity<HttpStatus> update(
        @RequestBody WorkerRequest workerRequest
    ) {
        log.info("Received: update hash request - {}", workerRequest.requestId());
        try {
            managerService.update(workerRequest);
            return ResponseEntity.ok().build();
        } catch (NoSuchRequestException exception) {
            return ResponseEntity.notFound().build();
        } catch (ServiceException exception) {
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            log.error("{} {}", exception, exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
