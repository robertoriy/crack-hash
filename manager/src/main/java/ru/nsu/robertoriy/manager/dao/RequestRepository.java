package ru.nsu.robertoriy.manager.dao;

import java.util.UUID;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

public interface RequestRepository {
    UUID save(StatusResponse statusResponse);

    StatusResponse get(UUID requestId);

    void update(UUID requestId, StatusResponse statusResponse);
}
