package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.syncProjectPermission;

import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.hasProject.HasProjectOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertProjectPermission.UpsertProjectPermissionOperation;
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
class SyncProjectPermissionMethodImpl implements SyncProjectPermissionMethod {

    final UpsertProjectPermissionOperation upsertProjectPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasProjectOperation hasProjectOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(final SyncProjectPermissionRequest request) {
        log.debug("Sync project permission, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var permission = request.getPermission();
        final var tenantId = permission.getTenantId();
        final var projectId = permission.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasProjectOperation
                                            .hasProject(sqlConnection, shard, tenantId, projectId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertProjectPermissionOperation.upsertProjectPermission(
                                                            changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            permission);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            "project does not exist or was deleted, id=" + projectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncProjectPermissionResponse::new);
    }
}
