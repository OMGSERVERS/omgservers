package com.omgservers.application.module.matchmakerModule.model.request;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
                               RequestConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, config);
    }

    public RequestModel create(Long id,
                               Long matchmakerId,
                               RequestConfigModel config) {
        Instant now = Instant.now();

        final var request = new RequestModel();
        request.setId(id);
        request.setMatchmakerId(matchmakerId);
        request.setCreated(now);
        request.setConfig(config);
        return request;
    }
}
