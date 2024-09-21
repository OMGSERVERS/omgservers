package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantStagePermissionsMethod {
    Uni<ViewTenantStagePermissionsResponse> execute(ViewTenantStagePermissionsRequest request);
}
