package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.hasTenantPermission;

import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);
}
