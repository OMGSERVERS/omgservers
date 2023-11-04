package com.omgservers.factory;

import com.omgservers.model.matchCommand.MatchCommandBodyModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
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

    public MatchCommandModel create(final Long matchmakerId,
                                    final Long matchId,
                                    final MatchCommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, body);
    }

    public MatchCommandModel create(final Long id,
                                    final Long matchmakerId,
                                    final Long matchId,
                                    final MatchCommandBodyModel body) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchCommand = new MatchCommandModel();
        matchCommand.setId(id);
        matchCommand.setMatchmakerId(matchmakerId);
        matchCommand.setMatchId(matchId);
        matchCommand.setCreated(now);
        matchCommand.setModified(now);
        matchCommand.setQualifier(body.getQualifier());
        matchCommand.setBody(body);
        return matchCommand;
    }
}
