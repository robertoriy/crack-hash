package ru.nsu.robertoriy.worker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "hash",
    "maxLength"
})
public record CrackRequest(
    @JsonProperty("hash") String hash,
    @JsonProperty("maxLength") int maxLength
) {
}
