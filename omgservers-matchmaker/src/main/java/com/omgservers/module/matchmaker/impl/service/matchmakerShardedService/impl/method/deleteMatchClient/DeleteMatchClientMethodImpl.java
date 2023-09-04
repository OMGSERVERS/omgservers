package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchClient;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchClient.DeleteMatchClientOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchClientMethodImpl implements DeleteMatchClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchClientOperation deleteMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request) {
        DeleteMatchClientShardedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteMatchClientShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                deleteMatchClientOperation.deleteMatchClient(changeContext, sqlConnection, shardModel.shard(), matchmakerId, id));
    }
}
