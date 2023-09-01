package com.omgservers.module.matchmaker.factory;

import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchClientModel create(final Long matchmakerId,
                                   final Long matchId,
                                   final Long userId,
                                   final Long clientId,
                                   final Long requestId) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, userId, clientId, requestId);
    }

    public MatchClientModel create(final Long id,
                                   final Long matchmakerId,
                                   final Long matchId,
                                   final Long userId,
                                   final Long clientId,
                                   final Long requestId) {
        Instant now = Instant.now();

        final var matchClient = new MatchClientModel();
        matchClient.setId(id);
        matchClient.setMatchmakerId(matchmakerId);
        matchClient.setMatchId(matchId);
        matchClient.setCreated(now);
        matchClient.setModified(now);
        matchClient.setUserId(userId);
        matchClient.setClientId(clientId);
        matchClient.setRequestId(requestId);
        return matchClient;
    }
}
