package ru.nsu.robertoriy.manager.repository;

import java.util.UUID;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

public interface ManagerRepository {
    UUID save(StatusResponse statusResponse);

    StatusResponse get(UUID requestId);

    void update(UUID requestId, StatusResponse statusResponse);

    void delete(UUID requestId);
}
