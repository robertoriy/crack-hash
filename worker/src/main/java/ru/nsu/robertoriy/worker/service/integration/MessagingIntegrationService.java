package ru.nsu.robertoriy.worker.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.configuration.RabbitMQPropertiesConfig;
import ru.nsu.robertoriy.worker.dto.request.WorkerRequest;

@Slf4j
@Service
@Profile("distributed")
public class MessagingIntegrationService implements IntegrationService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final String exchangeName;
    private final String resultRoutingKey;

    public MessagingIntegrationService(
        RabbitMQPropertiesConfig rabbitConfig,
        RabbitTemplate rabbitTemplate,
        ObjectMapper objectMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        exchangeName = rabbitConfig.exchangeName();
        resultRoutingKey = rabbitConfig.resultRoutingKey();
    }

    @Override
    public void sendDataToManager(WorkerRequest workerRequest) {
        log.info(
            "Sending data to manager through message queue, requestId - {}, data - {}",
            workerRequest.requestId(),
            workerRequest.data()
        );

        try {
            String json = objectMapper.writeValueAsString(workerRequest);

            rabbitTemplate.convertAndSend(exchangeName, resultRoutingKey, json);
        } catch (Exception e) {
            log.error("Error sending data to worker through message queue", e);
            throw new RuntimeException(e);
        }

        log.info("Sent data to manager through message queue");
    }
}
