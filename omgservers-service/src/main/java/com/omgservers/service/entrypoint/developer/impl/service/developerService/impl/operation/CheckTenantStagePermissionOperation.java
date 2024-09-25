package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CheckTenantStagePermissionOperation {
    Uni<Void> execute(final Long tenantId,
                      final Long tenantStageId,
                      final Long userId,
                      final TenantStagePermissionQualifierEnum permissionQualifier);
}
