package ru.nsu.robertoriy.worker.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Min(value = 1, message = "Word length must be positive")
    @Max(value = 7, message = "Word length must be less than 8")
    int maxWordLength,

    @NotBlank String managerUrl
) {
}
