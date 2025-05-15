package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectTenantDeploymentResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentResourceMethodImpl implements GetTenantDeploymentResourceMethod {

    final SelectTenantDeploymentResourceOperation selectTenantDeploymentResourceOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(final ShardModel shardModel,
                                                            final GetTenantDeploymentResourceRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentResourceOperation
                        .execute(sqlConnection, slot, tenantId, id))
                .map(GetTenantDeploymentResourceResponse::new);
    }
}
