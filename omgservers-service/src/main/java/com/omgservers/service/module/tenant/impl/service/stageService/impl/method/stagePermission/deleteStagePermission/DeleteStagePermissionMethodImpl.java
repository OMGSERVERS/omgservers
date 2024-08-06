package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.deleteStagePermission;

import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import com.omgservers.service.module.tenant.impl.operation.stagePermission.deleteStagePermission.DeleteStagePermissionOperation;
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
class DeleteStagePermissionMethodImpl implements DeleteStagePermissionMethod {

    final DeleteStagePermissionOperation deleteStagePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteStagePermissionResponse> deleteStagePermission(DeleteStagePermissionRequest request) {
        log.debug("Delete stage permission, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteStagePermissionOperation
                                        .deleteStagePermission(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteStagePermissionResponse::new);
    }
}
