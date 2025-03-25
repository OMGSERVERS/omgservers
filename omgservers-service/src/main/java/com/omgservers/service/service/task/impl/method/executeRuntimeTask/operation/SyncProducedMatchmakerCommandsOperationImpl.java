package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncProducedMatchmakerCommandsOperationImpl implements SyncProducedMatchmakerCommandsOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final List<MatchmakerCommandModel> producedMatchmakerCommands) {
        return Multi.createFrom().iterable(producedMatchmakerCommands)
                .onItem().transformToUniAndConcatenate(producedMatchmakerCommand -> {
                    final var matchmakerId = producedMatchmakerCommand.getMatchmakerId();
                    return syncMatchmakerCommand(producedMatchmakerCommand)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.error("Failed to sync, matchmakerId={}, {}:{}",
                                        matchmakerId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return Boolean.FALSE;
                            });
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> syncMatchmakerCommand(final MatchmakerCommandModel matchmakerCommand) {
        final var request = new SyncMatchmakerCommandRequest(matchmakerCommand);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
