package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantFilesArchive;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.UpsertTenantFilesArchiveOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
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
class SyncTenantFilesArchiveMethodImpl implements SyncTenantFilesArchiveMethod {

    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;
    final UpsertTenantFilesArchiveOperation upsertTenantFilesArchiveOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantFilesArchiveResponse> execute(
            final SyncTenantFilesArchiveRequest request) {
        log.debug("Sync tenant files archive, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantFilesArchive = request.getTenantFilesArchive();
        final var tenantId = tenantFilesArchive.getTenantId();
        final var tenantVersionId = tenantFilesArchive.getVersionId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantVersionId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantFilesArchiveOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    tenantFilesArchive);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "tenant version does not exist or was deleted, id=" +
                                                                    tenantVersionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantFilesArchiveResponse::new);
    }
}
