package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantPermission.DeleteTenantPermissionOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantPermissionMethodImpl implements DeleteTenantPermissionMethod {

    final DeleteTenantPermissionOperation deleteTenantPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantPermissionResponse> execute(final ShardModel shardModel,
                                                       final DeleteTenantPermissionRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantPermissionOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantPermissionResponse::new);
    }
}
