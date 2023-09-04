package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteRequest;

import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRequestMethodImpl implements DeleteRequestMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request) {
        DeleteRequestShardedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteRequestShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                deleteRequestOperation.deleteRequest(changeContext, sqlConnection, shardModel.shard(), matchmakerId, id));
    }
}
