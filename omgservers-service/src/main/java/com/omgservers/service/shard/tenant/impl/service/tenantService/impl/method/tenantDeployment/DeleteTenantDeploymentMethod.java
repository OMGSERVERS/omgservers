package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentMethod {
    Uni<DeleteTenantDeploymentResponse> execute(DeleteTenantDeploymentRequest request);
}
