package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.UpsertTenantStageOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantStageMethodImpl implements SyncTenantStageMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantStageOperation upsertTenantStageOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStageResponse> execute(final SyncTenantStageRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantStage = request.getTenantStage();
        final var tenantId = tenantStage.getTenantId();
        final var tenantProjectId = tenantStage.getProjectId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantProjectExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantProjectId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantStageOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            tenantStage);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "project does not exist or was deleted, id=" + tenantProjectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantStageResponse::new);
    }
}
