package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.hasTenantPermission;

import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request);
}
