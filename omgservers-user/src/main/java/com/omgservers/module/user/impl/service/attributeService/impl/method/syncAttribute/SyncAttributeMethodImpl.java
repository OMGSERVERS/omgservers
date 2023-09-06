package com.omgservers.module.user.impl.service.attributeService.impl.method.syncAttribute;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.module.user.impl.operation.upsertAttribute.UpsertAttributeOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncAttributeMethodImpl implements SyncAttributeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertAttributeOperation upsertAttributeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request) {
        SyncAttributeShardedRequest.validate(request);

        final var attribute = request.getAttribute();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertAttributeOperation.upsertAttribute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                attribute)
                ))
                .map(ChangeContext::getResult)
                .map(SyncAttributeShardedResponse::new);
    }
}
