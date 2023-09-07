package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.matchmaker.impl.operation.upsertRequest.UpsertRequestOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRequestMethodImpl implements SyncRequestMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertRequestOperation upsertRequestOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request) {
        SyncRequestRequest.validate(request);

        final var requestModel = request.getRequest();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, requestModel))
                .map(SyncRequestResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, RequestModel request) {
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertRequestOperation.upsertRequest(context, sqlConnection, shardModel.shard(), request))
                .map(ChangeContext::getResult);
    }
}
