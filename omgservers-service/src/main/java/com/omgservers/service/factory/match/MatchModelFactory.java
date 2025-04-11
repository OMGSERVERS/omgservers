package com.omgservers.service.factory.match;

import com.omgservers.schema.model.match.MatchConfigDto;
import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchModel create(final Long matchmakerId,
                             final MatchConfigDto matchConfig) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchConfig, idempotencyKey);
    }

    public MatchModel create(final Long id,
                             final Long matchmakerId,
                             final MatchConfigDto matchConfig) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchConfig, idempotencyKey);
    }

    public MatchModel create(final Long matchmakerId,
                             final MatchConfigDto matchConfig,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchConfig, idempotencyKey);
    }

    public MatchModel create(final Long id,
                             final Long matchmakerId,
                             final MatchConfigDto matchConfig,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        final var runtimeId = generateIdOperation.generateId();

        final var match = new MatchModel();
        match.setId(id);
        match.setIdempotencyKey(idempotencyKey);
        match.setCreated(now);
        match.setModified(now);
        match.setMatchmakerId(matchmakerId);
        match.setRuntimeId(runtimeId);
        match.setConfig(matchConfig);
        match.setDeleted(Boolean.FALSE);
        return match;
    }
}
