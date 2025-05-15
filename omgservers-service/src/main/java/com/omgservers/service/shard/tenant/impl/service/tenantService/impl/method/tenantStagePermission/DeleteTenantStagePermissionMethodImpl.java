package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.DeleteTenantStagePermissionOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantStagePermissionMethodImpl implements DeleteTenantStagePermissionMethod {

    final DeleteTenantStagePermissionOperation deleteTenantStagePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantStagePermissionResponse> execute(final ShardModel shardModel,
                                                            final DeleteTenantStagePermissionRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantStagePermissionOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantStagePermissionResponse::new);
    }
}
