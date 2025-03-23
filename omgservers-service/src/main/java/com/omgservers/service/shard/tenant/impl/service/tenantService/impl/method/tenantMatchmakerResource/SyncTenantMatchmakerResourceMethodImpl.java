package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.SyncTenantMatchmakerResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.VerifyTenantDeploymentExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.UpsertTenantMatchmakerResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantMatchmakerResourceMethodImpl implements SyncTenantMatchmakerResourceMethod {

    final UpsertTenantMatchmakerResourceOperation upsertTenantMatchmakerResourceOperation;
    final VerifyTenantDeploymentExistsOperation verifyTenantDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantMatchmakerResourceResponse> execute(
            final SyncTenantMatchmakerResourceRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantMatchmakerResource = request.getTenantMatchmakerResource();
        final var tenantId = tenantMatchmakerResource.getTenantId();
        final var deploymentId = tenantMatchmakerResource.getDeploymentId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantDeploymentExistsOperation
                                            .execute(sqlConnection, shard, tenantId, deploymentId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantMatchmakerResourceOperation
                                                            .execute(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    tenantMatchmakerResource);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + deploymentId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantMatchmakerResourceResponse::new);
    }
}
