package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.DeleteTenantStageOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantStageMethodImpl implements DeleteTenantStageMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteTenantStageOperation deleteTenantStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteTenantStageResponse> execute(final DeleteTenantStageRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, id))
                .map(DeleteTenantStageResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantStageOperation.execute(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult);
    }
}
