package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantProjectPermissionsOperationImpl implements DeleteTenantProjectPermissionsOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantProjectId) {
        return viewProjectPermissions(tenantId, tenantProjectId)
                .flatMap(tenantProjectPermissions -> Multi.createFrom().iterable(tenantProjectPermissions)
                        .onItem().transformToUniAndConcatenate(tenantProjectPermission ->
                                deleteProjectPermission(tenantId, tenantProjectPermission.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant project permission, " +
                                                            "tenantProject={}/{}, " +
                                                            "tenantProjectPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantProjectId,
                                                    tenantProjectPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }


    Uni<List<TenantProjectPermissionModel>> viewProjectPermissions(final Long tenantId, final Long tenantProjectId) {
        final var request = new ViewTenantProjectPermissionsRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantProjectPermissionsResponse::getTenantProjectPermissions);
    }

    Uni<Boolean> deleteProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantProjectPermissionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantProjectPermissionResponse::getDeleted);
    }
}
