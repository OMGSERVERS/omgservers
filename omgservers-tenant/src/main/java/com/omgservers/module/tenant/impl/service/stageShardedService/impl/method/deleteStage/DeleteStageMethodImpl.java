package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage;

import com.omgservers.ChangeContext;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.tenant.impl.operation.deleteStage.DeleteStageOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageMethodImpl implements DeleteStageMethod {

    final InternalModule internalModule;

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteStageOperation deleteStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteStageShardedResponse> deleteStage(final DeleteStageShardedRequest request) {
        DeleteStageShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, id))
                .map(DeleteStageShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteStageOperation.deleteStage(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult);
    }
}
