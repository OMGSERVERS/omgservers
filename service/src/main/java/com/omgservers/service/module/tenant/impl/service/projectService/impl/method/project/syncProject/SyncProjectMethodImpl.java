package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.syncProject;

import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenant.hasTenant.HasTenantOperation;
import com.omgservers.service.module.tenant.impl.operation.project.upsertProject.UpsertProjectOperation;
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
class SyncProjectMethodImpl implements SyncProjectMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertProjectOperation upsertProjectOperation;
    final CheckShardOperation checkShardOperation;
    final HasTenantOperation hasTenantOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        log.debug("Sync project, request={}", request);

        final var project = request.getProject();
        final var tenantId = project.getTenantId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasTenantOperation
                                            .hasTenant(sqlConnection, shard, tenantId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertProjectOperation.upsertProject(changeContext,
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
                .map(SyncProjectResponse::new);
    }
}
