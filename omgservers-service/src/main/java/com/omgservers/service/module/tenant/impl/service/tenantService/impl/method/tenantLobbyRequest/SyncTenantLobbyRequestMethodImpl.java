package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
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

    final UpsertTenantLobbyRequestOperation upsertTenantLobbyRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantLobbyRequestResponse> execute(final SyncTenantLobbyRequestRequest request) {
        log.debug("Sync tenant lobby request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionLobbyRequest = request.getTenantLobbyRequest();
        final var tenantId = versionLobbyRequest.getTenantId();
        final var versionId = versionLobbyRequest.getDeploymentId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantLobbyRequestOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionLobbyRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantLobbyRequestResponse::new);
    }
}
