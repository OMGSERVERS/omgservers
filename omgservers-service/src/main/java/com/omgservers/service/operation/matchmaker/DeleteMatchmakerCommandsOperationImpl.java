package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.ViewMatchmakerCommandsResponse;
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
class DeleteMatchmakerCommandsOperationImpl implements DeleteMatchmakerCommandsOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final Long matchmakerId) {
        return viewMatchmakerCommands(matchmakerId)
                .flatMap(matchmakerCommands -> Multi.createFrom().iterable(matchmakerCommands)
                        .onItem().transformToUniAndConcatenate(matchmakerCommand -> {
                            final var matchmakerCommandId = matchmakerCommand.getId();
                            return deleteMatchmakerCommands(matchmakerId, matchmakerCommandId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                matchmakerId,
                                                matchmakerCommandId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<MatchmakerCommandModel>> viewMatchmakerCommands(final Long matchmakerId) {
        final var request = new ViewMatchmakerCommandsRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(ViewMatchmakerCommandsResponse::getMatchmakerCommands);
    }

    Uni<Boolean> deleteMatchmakerCommands(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerCommandRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerCommandResponse::getDeleted);
    }
}
