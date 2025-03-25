package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface AuthorizeTenantStageRequestOperation {
    Uni<TenantStageAuthorization> execute(String tenant,
                                          String project,
                                          String stage,
                                          Long userId,
                                          TenantStagePermissionQualifierEnum permission);
}
