package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchClientModel create(final Long matchmakerId,
                                             final Long matchId,
                                             final Long userId,
                                             final Long clientId,
                                             final String groupName,
                                             final MatchmakerMatchClientConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchId, userId, clientId, groupName, config, idempotencyKey);
    }

    public MatchmakerMatchClientModel create(final Long matchmakerId,
                                             final Long matchId,
                                             final Long userId,
                                             final Long clientId,
                                             final String groupName,
                                             final MatchmakerMatchClientConfigModel config,
                                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, userId, clientId, groupName, config, idempotencyKey);
    }

    public MatchmakerMatchClientModel create(final Long id,
                                             final Long matchmakerId,
                                             final Long matchId,
                                             final Long userId,
                                             final Long clientId,
                                             final String groupName,
                                             final MatchmakerMatchClientConfigModel config,
                                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerMatchClient = new MatchmakerMatchClientModel();
        matchmakerMatchClient.setId(id);
        matchmakerMatchClient.setIdempotencyKey(idempotencyKey);
        matchmakerMatchClient.setMatchmakerId(matchmakerId);
        matchmakerMatchClient.setMatchId(matchId);
        matchmakerMatchClient.setCreated(now);
        matchmakerMatchClient.setModified(now);
        matchmakerMatchClient.setUserId(userId);
        matchmakerMatchClient.setClientId(clientId);
        matchmakerMatchClient.setGroupName(groupName);
        matchmakerMatchClient.setConfig(config);
        matchmakerMatchClient.setDeleted(false);
        return matchmakerMatchClient;
    }
}
