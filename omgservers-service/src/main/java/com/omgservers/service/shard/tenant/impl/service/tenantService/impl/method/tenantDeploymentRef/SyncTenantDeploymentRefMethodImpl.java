package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.SyncTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.SyncTenantDeploymentRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.UpsertTenantDeploymentRefOperation;
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
class SyncTenantDeploymentRefMethodImpl implements SyncTenantDeploymentRefMethod {

    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;
    final UpsertTenantDeploymentRefOperation upsertTenantDeploymentRefOperation;
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantDeploymentRefResponse> execute(final ShardModel shardModel,
                                                        final SyncTenantDeploymentRefRequest request) {
        log.debug("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantDeploymentRef = request.getTenantDeploymentRef();
        final var tenantId = tenantDeploymentRef.getTenantId();
        final var tenantStageId = tenantDeploymentRef.getStageId();
        final var tenantVersionId = tenantDeploymentRef.getVersionId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantStageExistsOperation.execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantStageId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return verifyTenantVersionExistsOperation
                                                .execute(sqlConnection, shardModel.slot(), tenantId, tenantVersionId)
                                                .flatMap(versionExists -> {
                                                    if (versionExists) {
                                                        return upsertTenantDeploymentRefOperation
                                                                .execute(changeContext,
                                                                        sqlConnection,
                                                                        shardModel.slot(),
                                                                        tenantDeploymentRef);
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
                .map(SyncTenantDeploymentRefResponse::new);
    }
}
