package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
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

    final VerifyTenantProjectExistsOperation verifyTenantProjectExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantVersionOperation upsertTenantVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantVersionResponse> execute(final SyncTenantVersionRequest request) {
        log.debug("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantVersion = request.getTenantVersion();
        final var tenantId = tenantVersion.getTenantId();
        final var tenantProjectId = tenantVersion.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantProjectExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantProjectId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantVersionOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            tenantVersion);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "project does not exist or was deleted, id=" + tenantProjectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantVersionResponse::new);
    }
}
