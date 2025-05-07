package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;

public record TenantAuthorization(Long tenantId,
                                  Long userId,
                                  TenantPermissionQualifierEnum permission) {
}
