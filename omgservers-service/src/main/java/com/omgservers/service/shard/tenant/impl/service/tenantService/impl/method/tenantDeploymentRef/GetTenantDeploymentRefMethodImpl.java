package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.SelectTenantDeploymentRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentRefMethodImpl implements GetTenantDeploymentRefMethod {

    final SelectTenantDeploymentRefOperation selectTenantDeploymentRefOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentRefResponse> execute(final ShardModel shardModel,
                                                       final GetTenantDeploymentRefRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentRefOperation
                        .execute(sqlConnection, slot, tenantId, id))
                .map(GetTenantDeploymentRefResponse::new);
    }
}
