package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.UpsertTenantStageOperation;
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
class SyncTenantStageMethodImpl implements SyncTenantStageMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantStageOperation upsertTenantStageOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStageResponse> execute(final SyncTenantStageRequest request) {
        log.debug("Sync stage, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var stage = request.getTenantStage();
        final var tenantId = stage.getTenantId();
        final var projectId = stage.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantProjectExistsOperation
                                            .execute(sqlConnection, shard, tenantId, projectId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantStageOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            stage);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "project does not exist or was deleted, id=" + projectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantStageResponse::new);
    }
}