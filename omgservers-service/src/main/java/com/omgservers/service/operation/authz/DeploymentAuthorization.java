package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;

public record DeploymentAuthorization(Long deploymentId,
                                      Long userId,
                                      Long tenantId,
                                      Long tenantStageId,
                                      TenantStagePermissionQualifierEnum permission) {
}
