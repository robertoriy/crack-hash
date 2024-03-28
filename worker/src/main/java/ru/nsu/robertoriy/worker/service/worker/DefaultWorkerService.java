package ru.nsu.robertoriy.worker.service.worker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;
import ru.nsu.robertoriy.worker.mapper.TaskMapper;
import ru.nsu.robertoriy.worker.service.decoder.Md5Decoder;
import ru.nsu.robertoriy.worker.service.integration.IntegrationService;

@RequiredArgsConstructor
@Service
public class DefaultWorkerService implements WorkerService {
    private final Md5Decoder md5Decoder;
    private final IntegrationService integrationService;
    private final ExecutorService executorService;

    public void completeTask(TaskRequest taskRequest) {
        executorService.submit(
            () -> {
                List<String> data = md5Decoder.decode(TaskMapper.INSTANCE.taskRequestToTask(taskRequest));
                if (!data.isEmpty()) {
                    integrationService.sendDataToManager(
                        new WorkerResponse(
                            taskRequest.requestId(),
                            data
                        )
                    );
                }
            }
        );
    }
}
