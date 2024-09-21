package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import io.smallrye.mutiny.Uni;

public interface VerifyTenantStagePermissionExistsMethod {
    Uni<VerifyTenantStagePermissionExistsResponse> execute(VerifyTenantStagePermissionExistsRequest request);
}
