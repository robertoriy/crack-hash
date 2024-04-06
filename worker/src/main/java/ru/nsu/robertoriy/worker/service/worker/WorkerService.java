package ru.nsu.robertoriy.worker.service.worker;

import ru.nsu.robertoriy.worker.dto.request.TaskRequest;

public interface WorkerService {
    void completeTask(TaskRequest taskRequest);
}
