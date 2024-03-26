package ru.nsu.robertoriy.worker.service;

import ru.nsu.robertoriy.worker.dto.request.ManagerRequest;

public interface HashCrackingService {
    void completeTask(ManagerRequest managerRequest);
}
