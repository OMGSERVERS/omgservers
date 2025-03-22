package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;

public record TenantVersionAuthorization(Long tenantId,
                                         Long tenantProjectId,
                                         Long tenantVersionId,
                                         Long userId,
                                         TenantProjectPermissionQualifierEnum permission) {
}
