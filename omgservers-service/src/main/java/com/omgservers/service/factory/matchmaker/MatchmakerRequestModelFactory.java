package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.request.MatchmakerRequestConfigModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerRequestModel create(Long matchmakerId,
                                         Long userId,
                                         Long clientId,
                                         String mode,
                                         MatchmakerRequestConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, userId, clientId, mode, config, idempotencyKey);
    }

    public MatchmakerRequestModel create(Long matchmakerId,
                                         Long userId,
                                         Long clientId,
                                         String mode,
                                         MatchmakerRequestConfigModel config,
                                         String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, userId, clientId, mode, config, idempotencyKey);
    }

    public MatchmakerRequestModel create(Long id,
                                         Long matchmakerId,
                                         Long userId,
                                         Long clientId,
                                         String mode,
                                         MatchmakerRequestConfigModel config,
                                         String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var request = new MatchmakerRequestModel();
        request.setId(id);
        request.setIdempotencyKey(idempotencyKey);
        request.setMatchmakerId(matchmakerId);
        request.setCreated(now);
        request.setModified(now);
        request.setUserId(userId);
        request.setClientId(clientId);
        request.setMode(mode);
        request.setConfig(config);
        request.setDeleted(false);
        return request;
    }
}
