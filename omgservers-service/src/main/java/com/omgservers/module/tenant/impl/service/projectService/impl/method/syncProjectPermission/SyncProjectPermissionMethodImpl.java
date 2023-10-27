package com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProjectPermission;

import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.tenant.impl.operation.upsertProjectPermission.UpsertProjectPermissionOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncProjectPermissionMethodImpl implements SyncProjectPermissionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertProjectPermissionOperation upsertProjectPermissionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request) {
        final var permission = request.getPermission();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, permission))
                .map(SyncProjectPermissionResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, ProjectPermissionModel permission) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertProjectPermissionOperation.upsertProjectPermission(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                permission))
                .map(ChangeContext::getResult);
    }
}
