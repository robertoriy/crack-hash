package ru.nsu.robertoriy.manager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

@JsonPropertyOrder({
    "requestId",
    "crackRequest"
})
public record ManagerRequest(
    @JsonProperty("requestId") UUID requestId,
    @JsonProperty("crackRequest") CrackRequest crackRequest
) {
}
