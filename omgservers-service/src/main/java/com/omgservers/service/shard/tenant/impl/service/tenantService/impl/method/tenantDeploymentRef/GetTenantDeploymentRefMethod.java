package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentRefMethod {
    Uni<GetTenantDeploymentRefResponse> execute(GetTenantDeploymentRefRequest request);
}
