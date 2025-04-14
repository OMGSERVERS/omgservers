package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.UpsertTenantDeploymentResourceOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantDeploymentResourceMethodImpl implements SyncTenantDeploymentResourceMethod {

    final UpsertTenantDeploymentResourceOperation upsertTenantDeploymentResourceOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantDeploymentResourceResponse> execute(final ShardModel shardModel,
                                                             final SyncTenantDeploymentResourceRequest request) {
        log.trace("{}", request);

        final var tenantDeploymentResource = request.getTenantDeploymentResource();
        final var tenantId = tenantDeploymentResource.getTenantId();
        final var tenantStageId = tenantDeploymentResource.getStageId();
        final var tenantVersionId = tenantDeploymentResource.getVersionId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantStageExistsOperation
                                .execute(sqlConnection, shardModel.shard(), tenantId, tenantStageId)
                                .flatMap(stageExists -> {
                                    if (stageExists) {
                                        return verifyTenantVersionExistsOperation
                                                .execute(sqlConnection, shardModel.shard(), tenantId, tenantVersionId)
                                                .flatMap(versionExists -> {
                                                    if (versionExists) {
                                                        return upsertTenantDeploymentResourceOperation
                                                                .execute(changeContext,
                                                                        sqlConnection,
                                                                        shardModel.shard(),
                                                                        tenantDeploymentResource);
                                                    } else {
                                                        throw new ServerSideNotFoundException(
                                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                                "version does not exist or was deleted, id=" +
                                                                        tenantVersionId);
                                                    }
                                                });
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "stage does not exist or was deleted, id=" +
                                                        tenantStageId);
                                    }
                                })

                )
                .map(ChangeContext::getResult)
                .map(SyncTenantDeploymentResourceResponse::new);
    }
}
