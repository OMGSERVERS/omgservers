package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
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
class CreateKickClientMatchmakerCommandOperationImpl implements CreateKickClientMatchmakerCommandOperation {

    final MatchmakerShard matchmakerShard;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public Uni<Boolean> executeFailSafe(final Long matchmakerId,
                                        final Long matchId,
                                        final Long clientId) {
        final var commandBody = new KickClientMatchmakerCommandBodyDto(clientId, matchId);
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(matchmakerCommand);
        return matchmakerShard.getService().execute(request)
                .map(SyncMatchmakerCommandResponse::getCreated)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
