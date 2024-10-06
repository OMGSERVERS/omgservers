package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CheckTenantStagePermissionOperationImpl implements CheckTenantStagePermissionOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long tenantStageId,
                             final Long userId,
                             final TenantStagePermissionQualifierEnum permissionQualifier) {
        final var request = new VerifyTenantStagePermissionExistsRequest(tenantId,
                tenantStageId,
                userId,
                permissionQualifier);
        return tenantModule.getTenantService().verifyTenantStagePermissionExists(request)
                .map(VerifyTenantStagePermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, tenantStageId=%d, userId=%d, permissionQualifier=%s",
                                        tenantId, tenantStageId, userId, permissionQualifier));
                    }
                })
                .replaceWithVoid();
    }
}