package com.omgservers.factory;

import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
                             final MatchConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var runtimeId = generateIdOperation.generateId();
        return create(id, matchmakerId, runtimeId, config);
    }

    public MatchModel create(final Long id,
                             final Long matchmakerId,
                             final Long runtimeId,
                             final MatchConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var match = new MatchModel();
        match.setId(id);
        match.setMatchmakerId(matchmakerId);
        match.setCreated(now);
        match.setModified(now);
        match.setRuntimeId(runtimeId);
        match.setStopped(Boolean.FALSE);
        match.setConfig(config);
        match.setDeleted(Boolean.FALSE);
        return match;
    }
}
