package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.DeleteTenantProjectPermissionOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantProjectPermissionMethodImpl implements DeleteTenantProjectPermissionMethod {

    final DeleteTenantProjectPermissionOperation deleteTenantProjectPermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> execute(final ShardModel shardModel,
                                                              final DeleteTenantProjectPermissionRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteTenantProjectPermissionOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantProjectPermissionResponse::new);
    }
}
