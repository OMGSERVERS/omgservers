package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.FindTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.FindTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantDeploymentRefMethod {
    Uni<FindTenantDeploymentRefResponse> execute(FindTenantDeploymentRefRequest request);
}
