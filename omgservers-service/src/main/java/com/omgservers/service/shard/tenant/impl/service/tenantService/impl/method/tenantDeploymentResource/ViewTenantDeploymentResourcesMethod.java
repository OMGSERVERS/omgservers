package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantDeploymentResourcesMethod {
    Uni<ViewTenantDeploymentResourcesResponse> execute(ViewTenantDeploymentResourcesRequest request);
}
