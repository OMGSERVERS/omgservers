package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentMethod {
    Uni<GetTenantDeploymentResponse> execute(GetTenantDeploymentRequest request);
}
