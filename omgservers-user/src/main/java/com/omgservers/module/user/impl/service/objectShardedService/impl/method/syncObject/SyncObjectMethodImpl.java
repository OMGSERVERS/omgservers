package com.omgservers.module.user.impl.service.objectShardedService.impl.method.syncObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.module.user.impl.operation.upsertObject.UpsertObjectOperation;
import com.omgservers.module.user.impl.operation.validateObject.ValidateObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncObjectMethodImpl implements SyncObjectMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final ValidateObjectOperation validateObjectOperation;
    final UpsertObjectOperation upsertObjectOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request) {
        SyncObjectShardedRequest.validate(request);

        final var userId = request.getUserId();
        final var object = request.getObject();
        validateObjectOperation.validateObject(object);
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertObjectOperation.upsertObject(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                userId,
                                object)))
                .map(ChangeContext::getResult)
                .map(SyncObjectShardedResponse::new);
    }
}
