package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectPermissionMethod {
    Uni<DeleteTenantProjectPermissionResponse> execute(DeleteTenantProjectPermissionRequest request);
}
