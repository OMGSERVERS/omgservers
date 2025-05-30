package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.UpsertTenantStageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantStageMethodImpl implements SyncTenantStageMethod {

    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantStageOperation upsertTenantStageOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantStageResponse> execute(final ShardModel shardModel,
                                                final SyncTenantStageRequest request) {
        log.debug("{}", request);

        final var tenantStage = request.getTenantStage();
        final var tenantId = tenantStage.getTenantId();
        final var tenantProjectId = tenantStage.getProjectId();

        final var slot = shardModel.slot();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantProjectExistsOperation.execute(sqlConnection, slot, tenantId, tenantProjectId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantStageOperation.execute(changeContext,
                                                sqlConnection,
                                                slot,
                                                tenantStage);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "project does not exist or was deleted, id=" + tenantProjectId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantStageResponse::new);
    }
}
