package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStagePermissionMethod {
    Uni<DeleteTenantStagePermissionResponse> execute(DeleteTenantStagePermissionRequest request);
}
