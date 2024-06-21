package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.viewTenantPermissions;

import com.omgservers.model.dto.tenant.ViewTenantPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantPermissionsMethod {
    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(ViewTenantPermissionsRequest request);
}
