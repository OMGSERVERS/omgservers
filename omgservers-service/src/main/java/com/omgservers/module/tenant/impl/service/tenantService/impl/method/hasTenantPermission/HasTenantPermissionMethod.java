package com.omgservers.module.tenant.impl.service.tenantService.impl.method.hasTenantPermission;

import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);
}
