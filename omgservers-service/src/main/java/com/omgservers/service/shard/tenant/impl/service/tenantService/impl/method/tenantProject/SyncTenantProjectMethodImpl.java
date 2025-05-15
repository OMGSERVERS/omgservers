package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenant.VerifyTenantExistsOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.UpsertTenantProjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncTenantProjectMethodImpl implements SyncTenantProjectMethod {

    final UpsertTenantProjectOperation upsertTenantProjectOperation;
    final VerifyTenantExistsOperation verifyTenantExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantProjectResponse> execute(final ShardModel shardModel,
                                                  final SyncTenantProjectRequest request) {
        log.debug("{}", request);

        final var project = request.getTenantProject();
        final var tenantId = project.getTenantId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        verifyTenantExistsOperation.execute(sqlConnection, shardModel.slot(), tenantId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertTenantProjectOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.slot(),
                                                project);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "tenant does not exist or was deleted, id=" + tenantId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncTenantProjectResponse::new);
    }
}
