package ru.nsu.robertoriy.manager.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Min(value = 1, message = "Word length must be positive")
    @Max(value = 6, message = "Word length must be less than 7")
    long maxWordLength
) {
}
