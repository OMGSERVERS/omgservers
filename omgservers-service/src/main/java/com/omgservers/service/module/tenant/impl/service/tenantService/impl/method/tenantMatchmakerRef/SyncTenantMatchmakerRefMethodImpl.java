package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.VerifyTenantDeploymentExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.UpsertTenantMatchmakerRefOperation;
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
class SyncTenantMatchmakerRefMethodImpl implements SyncTenantMatchmakerRefMethod {

    final VerifyTenantDeploymentExistsOperation verifyTenantDeploymentExistsOperation;
    final UpsertTenantMatchmakerRefOperation upsertTenantMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> execute(
            final SyncTenantMatchmakerRefRequest request) {
        log.trace("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionMatchmakerRef = request.getTenantMatchmakerRef();
        final var tenantId = versionMatchmakerRef.getTenantId();
        final var deploymentId = versionMatchmakerRef.getDeploymentId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantDeploymentExistsOperation
                                            .execute(sqlConnection, shard, tenantId, deploymentId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantMatchmakerRefOperation
                                                            .execute(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    versionMatchmakerRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "deployment does not exist or was deleted, id=" +
                                                                    deploymentId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantMatchmakerRefResponse::new);
    }
}
