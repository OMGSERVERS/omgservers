package com.omgservers.factory;

import com.omgservers.model.matchClient.MatchClientConfigModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchClientModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchClientModel create(final Long matchmakerId,
                                   final Long matchId,
                                   final Long userId,
                                   final Long clientId,
                                   final String groupName,
                                   final MatchClientConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, userId, clientId, groupName, config);
    }

    public MatchClientModel create(final Long id,
                                   final Long matchmakerId,
                                   final Long matchId,
                                   final Long userId,
                                   final Long clientId,
                                   final String groupName,
                                   final MatchClientConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchClient = new MatchClientModel();
        matchClient.setId(id);
        matchClient.setMatchmakerId(matchmakerId);
        matchClient.setMatchId(matchId);
        matchClient.setCreated(now);
        matchClient.setModified(now);
        matchClient.setUserId(userId);
        matchClient.setClientId(clientId);
        matchClient.setGroupName(groupName);
        matchClient.setConfig(config);
        return matchClient;
    }
}
