package ru.nsu.robertoriy.worker.service.worker;

import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;

public interface WorkerService {
    void completeTask(TaskRequest taskRequest);
}
