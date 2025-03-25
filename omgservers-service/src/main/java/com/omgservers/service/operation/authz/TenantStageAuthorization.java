package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;

public record TenantStageAuthorization(Long tenantId,
                                       Long tenantProjectId,
                                       Long tenantStageId,
                                       Long userId,
                                       TenantStagePermissionQualifierEnum permission) {
}
