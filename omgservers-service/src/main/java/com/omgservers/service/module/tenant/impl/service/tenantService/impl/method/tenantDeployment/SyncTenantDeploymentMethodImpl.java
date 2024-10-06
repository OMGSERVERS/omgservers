package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.UpsertTenantDeploymentOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantDeploymentMethodImpl implements SyncTenantDeploymentMethod {

    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;
    final UpsertTenantDeploymentOperation upsertTenantDeploymentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantDeploymentResponse> execute(final SyncTenantDeploymentRequest request) {
        log.debug("Sync tenant deployment, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantDeployment = request.getTenantDeployment();
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantStageId = tenantDeployment.getStageId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantStageExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantStageId)
                                            .flatMap(exists -> {
                                                // TODO: add version existence checking
                                                if (exists) {
                                                    return upsertTenantDeploymentOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shardModel.shard(),
                                                            tenantDeployment);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "stage does not exist or was deleted, id=" + tenantStageId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantDeploymentResponse::new);
    }
}
