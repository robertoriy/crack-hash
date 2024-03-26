package ru.nsu.robertoriy.manager.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.robertoriy.manager.dto.request.CrackRequest;
import ru.nsu.robertoriy.manager.dto.response.CrackResponse;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;
import ru.nsu.robertoriy.manager.service.ManagerService;
import ru.nsu.robertoriy.manager.service.exception.NoSuchRequestException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hash")
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/crack")
    public ResponseEntity<CrackResponse> crackHash(
        @RequestBody CrackRequest crackRequest
    ) {
        try {
            CrackResponse crackResponse = managerService.crack(crackRequest);
            log.info("Crack hash response - {}", crackResponse);
            return ResponseEntity.ok(crackResponse);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/status")
    public ResponseEntity<StatusResponse> getStatus(
        @RequestParam("requestId") UUID requestId
    ) {
        try {
            StatusResponse statusResponse = managerService.status(requestId);
            log.info("Status hash response - {}", statusResponse);
            return ResponseEntity.ok(statusResponse);
        } catch (NoSuchRequestException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
