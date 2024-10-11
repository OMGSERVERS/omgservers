package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.UpsertTenantBuildRequestOperation;
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
class SyncTenantBuildRequestMethodImpl implements SyncTenantBuildRequestMethod {

    final UpsertTenantBuildRequestOperation upsertTenantBuildRequestOperation;
    final VerifyTenantVersionExistsOperation verifyTenantVersionExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantBuildRequestResponse> execute(
            final SyncTenantBuildRequestRequest request) {
        log.debug("Sync tenant build request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantBuildRequest = request.getTenantBuildRequest();
        final var tenantId = tenantBuildRequest.getTenantId();
        final var tenantVersionId = tenantBuildRequest.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantVersionExistsOperation
                                            .execute(sqlConnection, shard, tenantId, tenantVersionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertTenantBuildRequestOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    tenantBuildRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + tenantVersionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantBuildRequestResponse::new);
    }
}
