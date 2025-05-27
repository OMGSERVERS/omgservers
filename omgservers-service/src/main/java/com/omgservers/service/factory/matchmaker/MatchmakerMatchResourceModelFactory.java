package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceConfigDto;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchResourceModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerMatchResourceModel create(final Long matchmakerId,
                                               final MatchmakerMatchResourceConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var matchId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, matchId, config, idempotencyKey);
    }

    public MatchmakerMatchResourceModel create(final Long matchmakerId,
                                               final MatchmakerMatchResourceConfigDto config,
                                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        final var matchId = generateIdOperation.generateId();
        return create(id, matchmakerId, matchId, config, idempotencyKey);
    }

    public MatchmakerMatchResourceModel create(final Long id,
                                               final Long matchmakerId,
                                               final Long matchId,
                                               final MatchmakerMatchResourceConfigDto config,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerMatchResource = new MatchmakerMatchResourceModel();
        matchmakerMatchResource.setId(id);
        matchmakerMatchResource.setIdempotencyKey(idempotencyKey);
        matchmakerMatchResource.setMatchmakerId(matchmakerId);
        matchmakerMatchResource.setCreated(now);
        matchmakerMatchResource.setModified(now);
        matchmakerMatchResource.setMatchId(matchId);
        matchmakerMatchResource.setStatus(MatchmakerMatchResourceStatusEnum.PENDING);
        matchmakerMatchResource.setConfig(config);
        matchmakerMatchResource.setDeleted(Boolean.FALSE);
        return matchmakerMatchResource;
    }
}
