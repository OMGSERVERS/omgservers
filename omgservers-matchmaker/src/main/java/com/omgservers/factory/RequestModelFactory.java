package com.omgservers.factory;

import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RequestModel create(Long matchmakerId,
                               Long userId,
                               Long clientId,
                               RequestConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, userId, clientId, config);
    }

    public RequestModel create(Long id,
                               Long matchmakerId,
                               Long userId,
                               Long clientId,
                               RequestConfigModel config) {
        Instant now = Instant.now();

        final var request = new RequestModel();
        request.setId(id);
        request.setMatchmakerId(matchmakerId);
        request.setCreated(now);
        request.setModified(now);
        request.setUserId(userId);
        request.setClientId(clientId);
        request.setConfig(config);
        return request;
    }
}
