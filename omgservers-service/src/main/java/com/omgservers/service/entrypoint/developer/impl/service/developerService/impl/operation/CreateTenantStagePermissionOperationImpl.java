package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantStagePermissionOperationImpl implements CreateTenantStagePermissionOperation {

    final TenantModule tenantModule;

    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;

    @Override
    public Uni<TenantStagePermissionModel> execute(final Long tenantId,
                                                   final Long tenantStageId,
                                                   final Long userId,
                                                   final TenantStagePermissionQualifierEnum permissionQualifier) {
        final var stagePermission = tenantStagePermissionModelFactory.create(tenantId,
                tenantStageId,
                userId,
                permissionQualifier);
        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getService().syncTenantStagePermission(request)
                .replaceWith(stagePermission);
    }
}
