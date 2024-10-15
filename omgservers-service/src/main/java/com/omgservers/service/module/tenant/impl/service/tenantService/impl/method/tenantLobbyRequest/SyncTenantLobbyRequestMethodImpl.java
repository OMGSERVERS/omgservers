package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.VerifyTenantDeploymentExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.UpsertTenantLobbyRequestOperation;
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
class SyncTenantLobbyRequestMethodImpl implements SyncTenantLobbyRequestMethod {

    final VerifyTenantDeploymentExistsOperation verifyTenantDeploymentExistsOperation;
    final UpsertTenantLobbyRequestOperation upsertTenantLobbyRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantLobbyRequestResponse> execute(final SyncTenantLobbyRequestRequest request) {
        log.debug("Sync tenant lobby request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantLobbyRequest = request.getTenantLobbyRequest();
        final var tenantId = tenantLobbyRequest.getTenantId();
        final var tenantDeploymentId = tenantLobbyRequest.getDeploymentId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantDeploymentExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantDeploymentId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantLobbyRequestOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    tenantLobbyRequest);
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
                .map(SyncTenantLobbyRequestResponse::new);
    }
}
