package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface AuthorizeDeploymentRequestOperation {
    Uni<DeploymentAuthorization> execute(Long deploymentId,
                                         Long userId,
                                         TenantStagePermissionQualifierEnum permission);
}
