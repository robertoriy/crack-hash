package ru.nsu.robertoriy.manager.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Min(value = 1, message = "Part count must be positive")
    @Max(value = 9, message = "Part Count must be less than 10")
    int partCount,

    @NotBlank String workerUrl,

    @Min(value = 10, message = "Max word length must be greater than 10 seconds")
    @Max(value = 300, message = "Part Count must be less than 5 minutes")
    int timeout,

    String alphabet
) {
}
