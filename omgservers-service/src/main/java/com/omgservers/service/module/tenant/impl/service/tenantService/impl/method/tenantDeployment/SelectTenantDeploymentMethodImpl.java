package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Select tenant deployment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantDeploymentsByTenantStageIdOperation
                                    .execute(sqlConnection, shardModel.shard(), tenantId, stageId)
                                    .map(tenantDeployments -> {
                                        if (tenantDeployments.isEmpty()) {
                                            throw new ServerSideNotFoundException(
                                                    ExceptionQualifierEnum.DEPLOYMENT_NOT_FOUND,
                                                    String.format("deployment was not selected, " +
                                                            "there aren't active stage deployments, " +
                                                            "stageId=%s", stageId));
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
