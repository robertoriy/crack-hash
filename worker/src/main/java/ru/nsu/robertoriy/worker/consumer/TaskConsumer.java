package ru.nsu.robertoriy.worker.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.exception.HashCrackingException;
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
        } catch (JsonProcessingException exception) {
            log.error("Error while converting json to dto", exception);
        } catch (HashCrackingException exception) {
            log.error("Error while completing the task", exception);
        } catch (Exception exception) {
            log.error("Unexpected exception while completing task: {}", exception.getMessage());
        }
    }
}
