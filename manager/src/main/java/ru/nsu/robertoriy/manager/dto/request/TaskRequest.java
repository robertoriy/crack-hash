package ru.nsu.robertoriy.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

@JsonPropertyOrder({
    "requestId",
    "hash",
    "alphabet",
    "maxLength",
    "partCount",
    "partNumber"
})
public record TaskRequest(
    @JsonProperty("requestId") UUID requestId,
    @JsonProperty("hash") String hash,
    @JsonProperty("alphabet") String alphabet,
    @JsonProperty("maxLength") int maxLength,
    @JsonProperty("partCount") int partCount,
    @JsonProperty("partNumber") int partNumber
) {
}
