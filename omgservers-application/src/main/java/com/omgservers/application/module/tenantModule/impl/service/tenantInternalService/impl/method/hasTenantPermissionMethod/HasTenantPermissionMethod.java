package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.hasTenantPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.HasTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);
}
