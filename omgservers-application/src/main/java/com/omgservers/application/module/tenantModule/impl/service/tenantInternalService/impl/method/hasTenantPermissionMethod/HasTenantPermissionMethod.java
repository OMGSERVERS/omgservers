package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.hasTenantPermissionMethod;

import com.omgservers.dto.tenantModule.HasTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request);
}
