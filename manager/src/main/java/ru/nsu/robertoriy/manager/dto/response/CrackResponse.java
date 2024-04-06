package ru.nsu.robertoriy.manager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

@JsonPropertyOrder({
    "requestId"
})
public record CrackResponse(
    @JsonProperty("requestId") UUID requestId
) {
}
