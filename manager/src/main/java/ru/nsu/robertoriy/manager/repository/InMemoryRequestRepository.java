package ru.nsu.robertoriy.manager.repository;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

@Component
public class InMemoryRequestRepository implements RequestRepository {
    private final Map<UUID, StatusResponse> statusResponses;

    @Autowired
    public InMemoryRequestRepository(Map<UUID, StatusResponse> statusResponses) {
        this.statusResponses = statusResponses;
    }

    @Override
    public UUID save(StatusResponse statusResponse) {
        UUID requestId = UUID.randomUUID();
        statusResponses.put(requestId, statusResponse);
        return requestId;
    }

    @Override
    public StatusResponse get(UUID requestId) {
        if (!statusResponses.containsKey(requestId)) {
            throw new NoSuchElementException();
        }
        return statusResponses.get(requestId);
    }

    @Override
    public void update(UUID requestId, StatusResponse statusResponse) {
        if (!statusResponses.containsKey(requestId)) {
            throw new NoSuchElementException();
        }
        statusResponses.put(requestId, statusResponse);
    }

    @Override
    public void delete(UUID requestId) {
        statusResponses.remove(requestId);
    }
}
