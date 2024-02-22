package com.omgservers.service.factory;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchRuntimeRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchRuntimeRefModel create(final Long matchmakerId,
                                       final Long matchId,
                                       final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, runtimeId);
    }

    public MatchRuntimeRefModel create(final Long id,
                                       final Long matchmakerId,
                                       final Long matchId,
                                       final Long runtimeId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchRuntimeRefModel = new MatchRuntimeRefModel();
        matchRuntimeRefModel.setId(id);
        matchRuntimeRefModel.setMatchmakerId(matchmakerId);
        matchRuntimeRefModel.setMatchId(matchId);
        matchRuntimeRefModel.setCreated(now);
        matchRuntimeRefModel.setModified(now);
        matchRuntimeRefModel.setRuntimeId(runtimeId);
        matchRuntimeRefModel.setDeleted(false);
        return matchRuntimeRefModel;
    }
}
