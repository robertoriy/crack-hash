package ru.nsu.robertoriy.manager.service.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.manager.configuration.RabbitMQPropertiesConfig;
import ru.nsu.robertoriy.manager.dto.request.TaskRequest;

@Slf4j
@Service
@Profile("distributed")
public class MessagingIntegrationService implements IntegrationService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final String exchangeName;
    private final String taskRoutingKey;

    public MessagingIntegrationService(
        RabbitMQPropertiesConfig rabbitConfig,
        RabbitTemplate rabbitTemplate,
        ObjectMapper objectMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        exchangeName = rabbitConfig.exchangeName();
        taskRoutingKey = rabbitConfig.taskRoutingKey();
    }

    @Async
    @Override
    public void sendTaskToWorkers(TaskRequest taskRequest) {
        log.info("Sending data to worker through message queue, task - {}", taskRequest);

        try {
            String json = objectMapper.writeValueAsString(taskRequest);

            rabbitTemplate.convertAndSend(exchangeName, taskRoutingKey, json);
        } catch (JsonProcessingException exception) {
            log.error("Error while converting dto to json", exception);
        } catch (Exception exception) {
            log.error("Error sending data to worker through message queue", exception);
        }

        log.info("Sent data to worker through message queue");
    }
}
