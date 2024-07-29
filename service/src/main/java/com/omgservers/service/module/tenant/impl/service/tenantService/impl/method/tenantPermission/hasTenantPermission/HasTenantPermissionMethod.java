package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.hasTenantPermission;

import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);
}
