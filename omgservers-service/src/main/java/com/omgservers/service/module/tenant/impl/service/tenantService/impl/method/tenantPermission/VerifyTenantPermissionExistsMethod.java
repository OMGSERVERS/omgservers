package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import io.smallrye.mutiny.Uni;

public interface VerifyTenantPermissionExistsMethod {
    Uni<VerifyTenantPermissionExistsResponse> execute(VerifyTenantPermissionExistsRequest request);
}
