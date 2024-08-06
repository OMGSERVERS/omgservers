package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerAssignmentModel create(final Long matchmakerId,
                                            final Long clientId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, clientId, idempotencyKey);
    }

    public MatchmakerAssignmentModel create(final Long matchmakerId,
                                            final Long clientId,
                                            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, clientId, idempotencyKey);
    }

    public MatchmakerAssignmentModel create(final Long id,
                                            final Long matchmakerId,
                                            final Long clientId,
                                            final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerAssignment = new MatchmakerAssignmentModel();
        matchmakerAssignment.setId(id);
        matchmakerAssignment.setIdempotencyKey(idempotencyKey);
        matchmakerAssignment.setMatchmakerId(matchmakerId);
        matchmakerAssignment.setCreated(now);
        matchmakerAssignment.setModified(now);
        matchmakerAssignment.setClientId(clientId);
        matchmakerAssignment.setDeleted(false);
        return matchmakerAssignment;
    }
}
