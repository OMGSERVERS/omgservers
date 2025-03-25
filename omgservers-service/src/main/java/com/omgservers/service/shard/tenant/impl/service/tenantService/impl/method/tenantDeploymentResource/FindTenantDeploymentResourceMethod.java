package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantDeploymentResourceMethod {
    Uni<FindTenantDeploymentResourceResponse> execute(FindTenantDeploymentResourceRequest request);
}
