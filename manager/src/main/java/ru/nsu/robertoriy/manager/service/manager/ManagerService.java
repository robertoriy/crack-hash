package ru.nsu.robertoriy.manager.service;

import java.util.UUID;
import ru.nsu.robertoriy.manager.dto.request.CrackRequest;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.dto.response.CrackResponse;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

public interface ManagerService {
    CrackResponse crack(CrackRequest crackRequest);

    StatusResponse status(UUID requestId);

    void update(WorkerRequest workerRequest);
}
