package ru.nsu.robertoriy.worker.service;

import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;

public interface HashCrackingService {
//    void completeTask(TaskRequest taskRequest);
    WorkerResponse completeTask(TaskRequest taskRequest);
}
