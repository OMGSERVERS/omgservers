package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.deleteTenantPermission;

import com.omgservers.model.dto.tenant.DeleteTenantPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionMethod {
    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);
}
