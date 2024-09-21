package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.UpsertTenantLobbyRefOperation;
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
class SyncTenantLobbyRefMethodImpl implements SyncTenantLobbyRefMethod {

    final UpsertTenantLobbyRefOperation upsertTenantLobbyRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantLobbyRefResponse> execute(final SyncTenantLobbyRefRequest request) {
        log.debug("Sync tenant lobby ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionLobbyRef = request.getTenantLobbyRef();
        final var tenantId = versionLobbyRef.getTenantId();
        final var versionId = versionLobbyRef.getDeploymentId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantLobbyRefOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionLobbyRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantLobbyRefResponse::new);
    }
}
