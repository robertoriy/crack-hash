package ru.nsu.robertoriy.worker.consumer;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.service.worker.WorkerService;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("distributed")
public class TaskConsumer {
    private final WorkerService workerService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${messaging.task-queue}")
    public void receiveTask(String jsonTaskRequest) {
        try {
            TaskRequest taskRequest = objectMapper.readValue(jsonTaskRequest, TaskRequest.class);
            log.info("Received from message queue: task - {}", taskRequest);

            List<String> data = workerService.completeTask(taskRequest);
            workerService.sendTask(taskRequest.requestId(), data);
        } catch (Exception e) {
            log.error("{} {}", e, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
