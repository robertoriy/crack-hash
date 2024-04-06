package ru.nsu.robertoriy.worker.service.worker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerRequest;
import ru.nsu.robertoriy.worker.mapper.TaskMapper;
import ru.nsu.robertoriy.worker.service.decoder.Md5Decoder;
import ru.nsu.robertoriy.worker.service.integration.IntegrationService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultWorkerService implements WorkerService {
    private final Md5Decoder md5Decoder;
    private final IntegrationService integrationService;
    private final ExecutorService executorService;

    public void completeTask(TaskRequest taskRequest) {
        log.info("Completing task for request id: {}", taskRequest.requestId());

        executorService.submit(
            () -> {
                List<String> data = md5Decoder.decode(TaskMapper.INSTANCE.taskRequestToTask(taskRequest));

                if (!data.isEmpty()) {
                    integrationService.sendDataToManager(
                        new WorkerRequest(
                            taskRequest.requestId(),
                            data
                        )
                    );
                }
            }
        );

        log.info("The task with requestId - {} has been submitted for execution", taskRequest.requestId());
    }
}
