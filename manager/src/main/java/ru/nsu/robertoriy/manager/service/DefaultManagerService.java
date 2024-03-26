package ru.nsu.robertoriy.manager.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.manager.dto.request.CrackRequest;
import ru.nsu.robertoriy.manager.dto.request.ManagerRequest;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.dto.response.CrackResponse;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;
import ru.nsu.robertoriy.manager.model.CrackStatus;
import ru.nsu.robertoriy.manager.repository.ManagerRepository;
import ru.nsu.robertoriy.manager.service.exception.NoSuchRequestException;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultManagerService implements ManagerService {
    private final ManagerRepository managerRepository;

    @Override
    public CrackResponse crack(CrackRequest crackRequest) {
        log.info("Cracking hash");
        log.info("Hash - {}, maxLength - {}", crackRequest.hash(), crackRequest.maxLength());

        // save
        StatusResponse statusToSave = new StatusResponse(CrackStatus.IN_PROGRESS, null);
        UUID requestId = managerRepository.save(statusToSave);
        log.info("Generated RequestId - {}", requestId);

        // send to worker
        ManagerRequest managerRequest = new ManagerRequest(requestId, crackRequest);
        log.info("ManagerRequest - {}", managerRequest);
        // webclient.send
        log.info("Sending task to worker");

        return new CrackResponse(requestId);
    }

    @Override
    public StatusResponse status(UUID requestId) {
        log.info("Returning status of crack");
        log.info("requestId - {}", requestId);

        try {
            return managerRepository.get(requestId);
        } catch (NoSuchElementException exception) {
            log.info("Getting status error: Repository doesn't contain the request");
            throw new NoSuchRequestException();
        }
    }

    @Override
    public void update(WorkerRequest workerRequest) {
        log.info("Updating hash");
        log.info("requestId - {}, data - {}", workerRequest.requestId(), workerRequest.data());

        StatusResponse statusToUpdate = new StatusResponse(CrackStatus.READY, workerRequest.data());
        try {
            managerRepository.update(workerRequest.requestId(), statusToUpdate);
        } catch (NoSuchElementException exception) {
            log.error("Updating error: Repository doesn't contain the request");
            throw new NoSuchRequestException();
        }
    }
}
