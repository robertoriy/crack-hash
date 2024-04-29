package ru.nsu.robertoriy.manager.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "messaging", ignoreUnknownFields = false)
public record RabbitMQPropertiesConfig(
    @NotBlank String exchangeName,
    @NotBlank String taskQueue,
    @NotBlank String taskRoutingKey,
    @NotBlank String resultQueue,
    @NotBlank String resultRoutingKey
) {
}
