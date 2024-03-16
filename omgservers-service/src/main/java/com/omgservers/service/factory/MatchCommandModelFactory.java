package com.omgservers.service.factory;

import com.omgservers.model.matchCommand.MatchCommandBodyModel;
import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchCommandModel create(final Long matchmakerId,
                                              final Long matchId,
                                              final MatchCommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, body);
    }

    public MatchmakerMatchCommandModel create(final Long id,
                                              final Long matchmakerId,
                                              final Long matchId,
                                              final MatchCommandBodyModel body) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchCommand = new MatchmakerMatchCommandModel();
        matchCommand.setId(id);
        matchCommand.setMatchmakerId(matchmakerId);
        matchCommand.setMatchId(matchId);
        matchCommand.setCreated(now);
        matchCommand.setModified(now);
        matchCommand.setQualifier(body.getQualifier());
        matchCommand.setBody(body);
        matchCommand.setDeleted(false);
        return matchCommand;
    }
}
