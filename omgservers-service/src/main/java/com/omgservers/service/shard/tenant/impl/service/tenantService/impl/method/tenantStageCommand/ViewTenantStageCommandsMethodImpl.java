package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.SelectActiveTenantStageCommandsByTenantStageIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantStageCommandsMethodImpl implements ViewTenantStageCommandsMethod {

    final SelectActiveTenantStageCommandsByTenantStageIdOperation selectActiveTenantStageCommandsByTenantStageIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantStageCommandResponse> execute(final ShardModel shardModel,
                                                       final ViewTenantStageCommandRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(sqlConnection ->
                        selectActiveTenantStageCommandsByTenantStageIdOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                tenantStageId
                        ))
                .map(ViewTenantStageCommandResponse::new);
    }
}
