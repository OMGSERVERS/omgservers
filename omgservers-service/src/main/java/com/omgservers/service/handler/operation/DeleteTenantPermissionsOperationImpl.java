package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.module.tenant.TenantModule;
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
class DeleteTenantPermissionsOperationImpl implements DeleteTenantPermissionsOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId) {
        return viewTenantPermissions(tenantId)
                .flatMap(tenantPermissions -> Multi.createFrom().iterable(tenantPermissions)
                        .onItem().transformToUniAndConcatenate(tenantPermission ->
                                deleteTenantPermission(tenantId, tenantPermission.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant permission, " +
                                                            "tenantId={}, " +
                                                            "tenantPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantPermissionModel>> viewTenantPermissions(final Long tenantId) {
        final var request = new ViewTenantPermissionsRequest(tenantId);
        return tenantModule.getTenantService().viewTenantPermissions(request)
                .map(ViewTenantPermissionsResponse::getTenantPermissions);
    }

    Uni<Boolean> deleteTenantPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantPermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantPermission(request)
                .map(DeleteTenantPermissionResponse::getDeleted);
    }
}
