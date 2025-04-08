package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.DeleteTenantStageOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<DeleteTenantStageResponse> execute(final ShardModel shardModel,
                                                  final DeleteTenantStageRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantStageOperation.execute(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantStageResponse::new);
    }
}
