package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionMethod {
    Uni<DeleteTenantPermissionResponse> execute(DeleteTenantPermissionRequest request);
}
