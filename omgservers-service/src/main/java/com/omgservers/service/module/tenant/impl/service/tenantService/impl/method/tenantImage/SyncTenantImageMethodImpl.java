package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.UpsertTenantImageOperation;
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
class SyncTenantImageMethodImpl implements SyncTenantImageMethod {

    final UpsertTenantImageOperation upsertTenantImageOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantImageResponse> execute(final SyncTenantImageRequest request) {
        log.trace("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantImage = request.getTenantImage();
        final var tenantId = tenantImage.getTenantId();
        final var tenantVersionId = tenantImage.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantVersionId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantImageOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    tenantImage);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + tenantVersionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantImageResponse::new);
    }
}
