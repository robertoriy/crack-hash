package ru.nsu.robertoriy.worker.service.worker;

import java.util.List;
import java.util.UUID;
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

    public List<String> completeTask(TaskRequest taskRequest) {
        log.info("Completing task for request id: {}", taskRequest.requestId());

        return md5Decoder.decode(TaskMapper.INSTANCE.taskRequestToTask(taskRequest));
    }

    @Override
    public void sendTask(UUID requestId, List<String> data) {
        integrationService.sendDataToManager(
            new WorkerRequest(
                requestId,
                data
            )
        );
        log.info("The task with requestId - {} has been completed", requestId);
    }
}
