package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.deleteProjectPermission;

import com.omgservers.schema.module.tenant.DeleteProjectPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteProjectPermissionResponse;
import com.omgservers.service.module.tenant.impl.operation.projectPermission.deleteProjectPermission.DeleteProjectPermissionOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectPermissionMethodImpl implements DeleteProjectPermissionMethod {

    final DeleteProjectPermissionOperation deleteProjectPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteProjectPermissionResponse> deleteProjectPermission(final DeleteProjectPermissionRequest request) {
        log.debug("Delete project permission, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteProjectPermissionOperation
                                        .deleteProjectPermission(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteProjectPermissionResponse::new);
    }
}
