package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.dto.matchmaker.DeleteRequestResponse;
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
    public Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request) {
        DeleteRequestRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteRequestResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRequestOperation.deleteRequest(changeContext, sqlConnection, shardModel.shard(), matchmakerId, id))
                .map(ChangeContext::getResult);
    }
}
