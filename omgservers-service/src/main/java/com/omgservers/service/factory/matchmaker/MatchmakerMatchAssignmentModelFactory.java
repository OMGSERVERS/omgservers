package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchAssignmentModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchAssignmentModel create(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long userId,
                                                 final Long clientId,
                                                 final String groupName,
                                                 final MatchmakerMatchAssignmentConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchId, userId, clientId, groupName, config, idempotencyKey);
    }

    public MatchmakerMatchAssignmentModel create(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long userId,
                                                 final Long clientId,
                                                 final String groupName,
                                                 final MatchmakerMatchAssignmentConfigDto config,
                                                 final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, userId, clientId, groupName, config, idempotencyKey);
    }

    public MatchmakerMatchAssignmentModel create(final Long id,
                                                 final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long userId,
                                                 final Long clientId,
                                                 final String groupName,
                                                 final MatchmakerMatchAssignmentConfigDto config,
                                                 final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerMatchAssignment = new MatchmakerMatchAssignmentModel();
        matchmakerMatchAssignment.setId(id);
        matchmakerMatchAssignment.setIdempotencyKey(idempotencyKey);
        matchmakerMatchAssignment.setMatchmakerId(matchmakerId);
        matchmakerMatchAssignment.setMatchId(matchId);
        matchmakerMatchAssignment.setCreated(now);
        matchmakerMatchAssignment.setModified(now);
        matchmakerMatchAssignment.setUserId(userId);
        matchmakerMatchAssignment.setClientId(clientId);
        matchmakerMatchAssignment.setGroupName(groupName);
        matchmakerMatchAssignment.setConfig(config);
        matchmakerMatchAssignment.setDeleted(false);
        return matchmakerMatchAssignment;
    }
}
