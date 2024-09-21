package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
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

    final UpsertTenantMatchmakerRefOperation upsertTenantMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> execute(
            final SyncTenantMatchmakerRefRequest request) {
        log.debug("Sync tenant matchmaker ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionMatchmakerRef = request.getTenantMatchmakerRef();
        final var tenantId = versionMatchmakerRef.getTenantId();
        final var versionId = versionMatchmakerRef.getDeploymentId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantMatchmakerRefOperation
                                                            .execute(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    versionMatchmakerRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantMatchmakerRefResponse::new);
    }
}
