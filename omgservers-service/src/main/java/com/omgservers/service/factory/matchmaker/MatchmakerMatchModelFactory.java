package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchModel create(final Long matchmakerId,
                                       final MatchmakerMatchConfigDto matchmakerMatchConfig) {
        final var id = generateIdOperation.generateId();
        final var runtimeId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, runtimeId, matchmakerMatchConfig, idempotencyKey);
    }

    public MatchmakerMatchModel create(final Long matchmakerId,
                                       final MatchmakerMatchConfigDto matchmakerMatchConfig,
                                       final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var runtimeId = generateIdOperation.generateId();
        return create(id, matchmakerId, runtimeId, matchmakerMatchConfig, idempotencyKey);
    }

    public MatchmakerMatchModel create(final Long id,
                                       final Long matchmakerId,
                                       final Long runtimeId,
                                       final MatchmakerMatchConfigDto matchmakerMatchConfig,
                                       final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerMatch = new MatchmakerMatchModel();
        matchmakerMatch.setId(id);
        matchmakerMatch.setIdempotencyKey(idempotencyKey);
        matchmakerMatch.setMatchmakerId(matchmakerId);
        matchmakerMatch.setCreated(now);
        matchmakerMatch.setModified(now);
        matchmakerMatch.setRuntimeId(runtimeId);
        matchmakerMatch.setConfig(matchmakerMatchConfig);
        matchmakerMatch.setStatus(MatchmakerMatchStatusEnum.PENDING);
        matchmakerMatch.setDeleted(Boolean.FALSE);
        return matchmakerMatch;
    }
}
