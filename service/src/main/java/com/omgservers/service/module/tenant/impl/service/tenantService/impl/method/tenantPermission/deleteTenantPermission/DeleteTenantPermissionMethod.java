package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.deleteTenantPermission;

import com.omgservers.schema.module.tenant.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionMethod {
    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);
}
