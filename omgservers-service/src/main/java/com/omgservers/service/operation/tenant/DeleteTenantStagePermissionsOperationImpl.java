package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
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
class DeleteTenantStagePermissionsOperationImpl implements DeleteTenantStagePermissionsOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantStageId) {
        return viewTenantStagePermissions(tenantId, tenantStageId)
                .flatMap(tenantStagePermissions -> Multi.createFrom().iterable(tenantStagePermissions)
                        .onItem().transformToUniAndConcatenate(tenantStagePermission ->
                                deleteTenantStagePermission(tenantId, tenantStagePermission.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant stage permission, " +
                                                            "tenantStage={}/{}, " +
                                                            "tenantStagePermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantStageId,
                                                    tenantStagePermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantStagePermissionModel>> viewTenantStagePermissions(final Long tenantId, final Long tenantStageId) {
        final var request = new ViewTenantStagePermissionsRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }

    Uni<Boolean> deleteTenantStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantStagePermissionRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantStagePermissionResponse::getDeleted);
    }
}
