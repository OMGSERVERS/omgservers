package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantDeploymentMethod {
    Uni<SyncTenantDeploymentResponse> execute(SyncTenantDeploymentRequest request);
}
