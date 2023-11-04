package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.syncRequest;

import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.service.module.matchmaker.impl.operation.upsertRequest.UpsertRequestOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        final var requestModel = request.getRequest();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (context, sqlConnection) -> upsertRequestOperation
                                        .upsertRequest(context, sqlConnection, shardModel.shard(), requestModel))
                        .map(ChangeContext::getResult))
                .map(SyncRequestResponse::new);
    }
}
