package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerCommand.body.OpenMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateOpenMatchMatchmakerCommandOperationImpl implements CreateOpenMatchMatchmakerCommandOperation {

    final MatchmakerShard matchmakerShard;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public Uni<Boolean> execute(final Long matchmakerId,
                                final Long matchId) {
        final var commandBody = new OpenMatchMatchmakerCommandBodyDto(matchId);

        final var idempotencyKey = commandBody.getQualifier() + "/" + matchId;
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmakerId, commandBody, idempotencyKey);
        final var request = new SyncMatchmakerCommandRequest(matchmakerCommand);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }

    @Override
    public Uni<Boolean> executeFailSafe(final Long matchmakerId,
                                        final Long matchId) {
        return execute(matchmakerId, matchId)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, matchId={}, {}:{}",
                            matchId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
