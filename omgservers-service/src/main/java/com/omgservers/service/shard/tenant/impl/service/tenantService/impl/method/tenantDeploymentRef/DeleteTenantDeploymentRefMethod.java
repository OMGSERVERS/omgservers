package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentRefMethod {
    Uni<DeleteTenantDeploymentRefResponse> execute(DeleteTenantDeploymentRefRequest request);
}
