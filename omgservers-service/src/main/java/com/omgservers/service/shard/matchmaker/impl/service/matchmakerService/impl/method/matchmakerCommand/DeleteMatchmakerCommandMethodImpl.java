package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.DeleteMatchmakerCommandResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.DeleteMatchmakerCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerCommandMethodImpl implements DeleteMatchmakerCommandMethod {

    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteMatchmakerCommandResponse> execute(final ShardModel shardModel,
                                                        final DeleteMatchmakerCommandRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteMatchmakerCommandOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        matchmakerId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchmakerCommandResponse::new);
    }
}
