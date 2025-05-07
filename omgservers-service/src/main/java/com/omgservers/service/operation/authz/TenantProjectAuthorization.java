package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;

public record TenantProjectAuthorization(Long tenantId,
                                         Long tenantProjectId,
                                         Long userId,
                                         TenantProjectPermissionQualifierEnum permission) {
}
