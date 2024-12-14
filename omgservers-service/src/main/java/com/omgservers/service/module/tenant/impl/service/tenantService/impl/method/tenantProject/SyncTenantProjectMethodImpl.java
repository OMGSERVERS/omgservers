package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenant.VerifyTenantExistsOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.UpsertTenantProjectOperation;
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
class SyncTenantProjectMethodImpl implements SyncTenantProjectMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertTenantProjectOperation upsertTenantProjectOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyTenantExistsOperation verifyTenantExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncTenantProjectResponse> execute(final SyncTenantProjectRequest request) {
        log.trace("Requested, {}", request);

        final var project = request.getTenantProject();
        final var tenantId = project.getTenantId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyTenantExistsOperation
                                            .execute(sqlConnection, shard, tenantId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertTenantProjectOperation.execute(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            project);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "tenant does not exist or was deleted, id=" + tenantId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncTenantProjectResponse::new);
    }
}
