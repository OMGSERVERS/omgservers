package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.DeleteMatchmakerRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerRequestMethodImpl implements DeleteMatchmakerRequestMethod {

    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteMatchmakerRequestResponse> execute(final ShardModel shardModel,
                                                        final DeleteMatchmakerRequestRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerRequestOperation.execute(changeContext, sqlConnection,
                                shardModel.shard(), matchmakerId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteMatchmakerRequestResponse::new);
    }
}
