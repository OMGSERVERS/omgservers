package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.DeleteMatchmakerRequestOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerRequestMethodImpl implements DeleteMatchmakerRequestMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchmakerRequestResponse> execute(final DeleteMatchmakerRequestRequest request) {
        log.trace("Requested, {}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteMatchmakerRequestResponse::new);
    }

    Uni<Boolean> changeFunction(final ShardModel shardModel,
                                final Long matchmakerId,
                                final Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerRequestOperation.execute(changeContext, sqlConnection,
                                shardModel.shard(), matchmakerId,
                                id))
                .map(ChangeContext::getResult);
    }
}
