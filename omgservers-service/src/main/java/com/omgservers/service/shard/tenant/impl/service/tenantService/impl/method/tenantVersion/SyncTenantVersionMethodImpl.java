package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.VerifyTenantProjectExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.UpsertTenantVersionOperation;
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
    final UpsertTenantVersionOperation upsertTenantVersionOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantVersionResponse> execute(final ShardModel shardModel,
                                                  final SyncTenantVersionRequest request) {
        log.debug("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var tenantVersion = request.getTenantVersion();
        final var tenantId = tenantVersion.getTenantId();
        final var tenantProjectId = tenantVersion.getProjectId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantProjectExistsOperation.execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantProjectId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantVersionOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.slot(),
                                                tenantVersion);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "project does not exist or was deleted, id=" + tenantProjectId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantVersionResponse::new);
    }
}
