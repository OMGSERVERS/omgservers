package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.VerifyTenantStageExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.UpsertTenantVersionOperation;
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
class SyncTenantVersionMethodImpl implements SyncTenantVersionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantVersionOperation upsertTenantVersionOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantStageExistsOperation verifyTenantStageExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantVersionResponse> execute(final SyncTenantVersionRequest request) {
        log.debug("Sync version, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var version = request.getTenantVersion();
        final var tenantId = version.getTenantId();
        final var stageId = version.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantStageExistsOperation
                                            .execute(sqlConnection, shard, tenantId, stageId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantVersionOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            version);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "stage does not exist or was deleted, id=" + stageId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantVersionResponse::new);
    }
}
