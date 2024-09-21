package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import io.smallrye.mutiny.Uni;

public interface SelectTenantDeploymentMethod {
    Uni<SelectTenantDeploymentResponse> execute(SelectTenantDeploymentRequest request);
}
