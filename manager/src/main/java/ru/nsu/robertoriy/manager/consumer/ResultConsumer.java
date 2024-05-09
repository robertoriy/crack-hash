package ru.nsu.robertoriy.manager.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.manager.dto.request.WorkerRequest;
import ru.nsu.robertoriy.manager.exception.NoSuchRequestException;
import ru.nsu.robertoriy.manager.exception.ServiceException;
import ru.nsu.robertoriy.manager.service.manager.ManagerService;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("distributed")
public class ResultConsumer {
    private final ManagerService managerService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${messaging.result-queue}")
    public void receiveResult(String jsonWorkerRequest) {
        try {
            WorkerRequest workerRequest = objectMapper.readValue(jsonWorkerRequest, WorkerRequest.class);
            log.info("Received: update hash request - {}", workerRequest.requestId());
            managerService.update(workerRequest);
        } catch (JsonProcessingException exception) {
            log.error("Error while converting json to dto", exception);
        } catch (NoSuchRequestException exception) {
            log.error("No such request: {}", exception.getMessage());
        } catch (ServiceException exception) {
            log.error("Service exception: {}", exception.getMessage());
        } catch (Exception exception) {
            log.error("Unexpected exception while updating data: {}", exception.getMessage());
        }
    }
}
