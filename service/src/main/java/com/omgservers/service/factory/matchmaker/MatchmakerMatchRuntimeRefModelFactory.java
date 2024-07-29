package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchRuntimeRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchRuntimeRefModel create(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchId, runtimeId, idempotencyKey);
    }

    public MatchmakerMatchRuntimeRefModel create(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long runtimeId,
                                                 final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, runtimeId, idempotencyKey);
    }

    public MatchmakerMatchRuntimeRefModel create(final Long id,
                                                 final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long runtimeId,
                                                 final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerMatchRuntimeRefModel = new MatchmakerMatchRuntimeRefModel();
        matchmakerMatchRuntimeRefModel.setId(id);
        matchmakerMatchRuntimeRefModel.setIdempotencyKey(idempotencyKey);
        matchmakerMatchRuntimeRefModel.setMatchmakerId(matchmakerId);
        matchmakerMatchRuntimeRefModel.setMatchId(matchId);
        matchmakerMatchRuntimeRefModel.setCreated(now);
        matchmakerMatchRuntimeRefModel.setModified(now);
        matchmakerMatchRuntimeRefModel.setRuntimeId(runtimeId);
        matchmakerMatchRuntimeRefModel.setDeleted(false);
        return matchmakerMatchRuntimeRefModel;
    }
}
