package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantProjectPermissionsMethod {
    Uni<ViewTenantProjectPermissionsResponse> execute(ViewTenantProjectPermissionsRequest request);
}
