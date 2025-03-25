package com.omgservers.service.factory.matchmaker;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestConfigDto;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public MatchmakerRequestModel create(final Long matchmakerId,
                                         final Long clientId,
                                         final String mode,
                                         final MatchmakerRequestConfigDto config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, matchmakerId, clientId, mode, config, idempotencyKey);
    }

    public MatchmakerRequestModel create(final Long matchmakerId,
                                         final Long clientId,
                                         final String mode,
                                         final MatchmakerRequestConfigDto config,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, matchmakerId, clientId, mode, config, idempotencyKey);
    }

    public MatchmakerRequestModel create(final Long id,
                                         final Long matchmakerId,
                                         final Long clientId,
                                         final String mode,
                                         final MatchmakerRequestConfigDto config,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var matchmakerRequest = new MatchmakerRequestModel();
        matchmakerRequest.setId(id);
        matchmakerRequest.setIdempotencyKey(idempotencyKey);
        matchmakerRequest.setMatchmakerId(matchmakerId);
        matchmakerRequest.setCreated(now);
        matchmakerRequest.setModified(now);
        matchmakerRequest.setClientId(clientId);
        matchmakerRequest.setMode(mode);
        matchmakerRequest.setConfig(config);
        matchmakerRequest.setDeleted(false);
        return matchmakerRequest;
    }
}
