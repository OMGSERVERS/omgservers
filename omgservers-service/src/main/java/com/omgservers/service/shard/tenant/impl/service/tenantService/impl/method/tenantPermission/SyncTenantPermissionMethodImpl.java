package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenant.VerifyTenantExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantPermission.UpsertTenantPermissionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantPermissionMethodImpl implements SyncTenantPermissionMethod {

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;
    final VerifyTenantExistsOperation verifyTenantExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncTenantPermissionResponse> execute(final ShardModel shardModel,
                                                     final SyncTenantPermissionRequest request) {
        log.trace("{}", request);

        final var permission = request.getTenantPermission();
        final var tenantId = permission.getTenantId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantExistsOperation.execute(sqlConnection, shardModel.slot(), tenantId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantPermissionOperation.execute(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.slot(),
                                                permission);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "tenant does not exist or was deleted, id=" + tenantId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncTenantPermissionResponse::new);
    }
}
