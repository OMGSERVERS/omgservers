package com.omgservers.service.factory.matchmaker;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerCommandModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerCommandModel create(final Long matchmakerId,
                                         final MatchmakerCommandBodyModel body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, body, idempotencyKey);
    }

    public MatchmakerCommandModel create(final Long matchmakerId,
                                         final MatchmakerCommandBodyModel body,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, body, idempotencyKey);
    }

    public MatchmakerCommandModel create(final Long id,
                                         final Long matchmakerId,
                                         final MatchmakerCommandBodyModel body,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerCommand = new MatchmakerCommandModel();
        matchmakerCommand.setId(id);
        matchmakerCommand.setIdempotencyKey(idempotencyKey);
        matchmakerCommand.setMatchmakerId(matchmakerId);
        matchmakerCommand.setCreated(now);
        matchmakerCommand.setModified(now);
        matchmakerCommand.setQualifier(body.getQualifier());
        matchmakerCommand.setBody(body);
        matchmakerCommand.setDeleted(false);
        return matchmakerCommand;
    }
}
