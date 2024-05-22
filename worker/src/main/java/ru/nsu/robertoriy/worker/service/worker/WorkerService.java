package ru.nsu.robertoriy.worker.service.worker;

import java.util.List;
import java.util.UUID;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;

public interface WorkerService {
    List<String> completeTask(TaskRequest taskRequest);

    void sendTask(UUID requestId, List<String> data);
}
