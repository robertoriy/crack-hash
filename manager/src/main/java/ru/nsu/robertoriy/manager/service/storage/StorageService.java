package ru.nsu.robertoriy.manager.service.storage;

import java.util.List;
import java.util.UUID;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

public interface StorageService {
    UUID createRequest();

    StatusResponse getStatusResponse(UUID requestId);

    void updateRequest(UUID requestId, List<String> data);

    void updateStatusOnTimeout(UUID requestId);
}
