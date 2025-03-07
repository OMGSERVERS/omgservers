package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantStageIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantDeploymentMethodImpl implements SelectTenantDeploymentMethod {

    final SelectActiveTenantDeploymentsByTenantStageIdOperation
            selectActiveTenantDeploymentsByTenantStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SelectTenantDeploymentResponse> execute(final SelectTenantDeploymentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantDeploymentsByTenantStageIdOperation
                                    .execute(sqlConnection, shardModel.shard(), tenantId, tenantStageId)
                                    .map(tenantDeployments -> {
                                        if (tenantDeployments.isEmpty()) {
                                            throw new ServerSideNotFoundException(
                                                    ExceptionQualifierEnum.DEPLOYMENT_NOT_FOUND,
                                                    String.format("deployment was not selected, " +
                                                            "there aren't active stage deployments, " +
                                                            "tenantStageId=%s", tenantStageId));
                                        }

                                        final var strategy = request.getStrategy();
                                        return switch (strategy) {
                                            case LATEST -> tenantDeployments.get(tenantDeployments.size() - 1);
                                        };
                                    }));
                })
                .map(SelectTenantDeploymentResponse::new);
    }
}
