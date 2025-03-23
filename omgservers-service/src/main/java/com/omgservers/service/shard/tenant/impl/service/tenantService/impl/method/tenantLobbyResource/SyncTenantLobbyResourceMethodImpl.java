package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.VerifyTenantDeploymentExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.UpsertTenantLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantLobbyResourceMethodImpl implements SyncTenantLobbyResourceMethod {

    final VerifyTenantDeploymentExistsOperation verifyTenantDeploymentExistsOperation;
    final UpsertTenantLobbyResourceOperation upsertTenantLobbyResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantLobbyResourceResponse> execute(final SyncTenantLobbyResourceRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantLobbyResource = request.getTenantLobbyResource();
        final var tenantId = tenantLobbyResource.getTenantId();
        final var tenantDeploymentId = tenantLobbyResource.getDeploymentId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantDeploymentExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantDeploymentId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantLobbyResourceOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    tenantLobbyResource);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "deployment does not exist or was deleted, id=" +
                                                                    tenantDeploymentId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantLobbyResourceResponse::new);
    }
}
