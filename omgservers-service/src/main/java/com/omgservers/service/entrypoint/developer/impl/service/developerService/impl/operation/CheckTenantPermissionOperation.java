package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CheckTenantPermissionOperation {
    Uni<Void> execute(final Long tenantId,
                      final Long userId,
                      final TenantPermissionQualifierEnum permissionQualifier);
}
