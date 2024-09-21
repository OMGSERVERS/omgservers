package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.UpsertTenantImageRefOperation;
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
class SyncTenantImageRefMethodImpl implements SyncTenantImageRefMethod {

    final UpsertTenantImageRefOperation upsertTenantImageRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantImageRefResponse> execute(final SyncTenantImageRefRequest request) {
        log.debug("Sync tenant image ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionImageRef = request.getTenantImageRef();
        final var tenantId = versionImageRef.getTenantId();
        final var versionId = versionImageRef.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantImageRefOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionImageRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantImageRefResponse::new);
    }
}
