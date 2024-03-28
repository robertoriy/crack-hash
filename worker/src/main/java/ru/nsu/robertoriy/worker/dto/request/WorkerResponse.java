package ru.nsu.robertoriy.worker.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.UUID;

@JsonPropertyOrder({
    "requestId",
    "data"
})
public record WorkerResponse(
    @JsonProperty("requestId") UUID requestId,
    @JsonProperty("data") List<String> data
) {
}
