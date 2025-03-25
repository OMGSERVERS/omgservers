package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantDeploymentResourceMethod {
    Uni<SyncTenantDeploymentResourceResponse> execute(SyncTenantDeploymentResourceRequest request);
}
