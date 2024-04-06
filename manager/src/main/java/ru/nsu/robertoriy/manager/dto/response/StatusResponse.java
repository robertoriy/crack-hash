package ru.nsu.robertoriy.manager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import ru.nsu.robertoriy.manager.model.CrackStatus;

@JsonPropertyOrder({
    "status",
    "data"
})
public record StatusResponse(
    @JsonProperty("status") CrackStatus status,
    @JsonProperty("data") List<String> data
) {
}
