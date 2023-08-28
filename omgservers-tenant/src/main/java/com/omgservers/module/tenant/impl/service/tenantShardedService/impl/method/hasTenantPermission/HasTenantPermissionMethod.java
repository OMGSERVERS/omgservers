package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.hasTenantPermission;

import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface HasTenantPermissionMethod {
    Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request);
}
