package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.UpsertTenantImageOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.VerifyTenantVersionExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantImageMethodImpl implements SyncTenantImageMethod {

    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;
    final UpsertTenantImageOperation upsertTenantImageOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantImageResponse> execute(final ShardModel shardModel,
                                                final SyncTenantImageRequest request) {
        log.trace("{}", request);

        final var tenantImage = request.getTenantImage();
        final var tenantId = tenantImage.getTenantId();
        final var tenantVersionId = tenantImage.getVersionId();

        final var slot = shardModel.slot();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantVersionExistsOperation.execute(sqlConnection, slot, tenantId, tenantVersionId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantImageOperation
                                                .execute(changeContext,
                                                        sqlConnection,
                                                        shardModel.slot(),
                                                        tenantImage);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "version does not exist or was deleted, id=" + tenantVersionId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncTenantImageResponse::new);
    }
}
