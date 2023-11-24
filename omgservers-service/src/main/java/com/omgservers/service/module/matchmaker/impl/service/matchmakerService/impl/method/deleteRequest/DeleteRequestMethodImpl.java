package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteRequest;

import com.omgservers.model.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
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
class DeleteRequestMethodImpl implements DeleteRequestMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request) {
        log.debug("Delete request, request={}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, matchmakerId, id))
                .map(DeleteRequestResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long matchmakerId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRequestOperation.deleteRequest(changeContext, sqlConnection, shardModel.shard(), matchmakerId,
                                id))
                .map(ChangeContext::getResult);
    }
}
