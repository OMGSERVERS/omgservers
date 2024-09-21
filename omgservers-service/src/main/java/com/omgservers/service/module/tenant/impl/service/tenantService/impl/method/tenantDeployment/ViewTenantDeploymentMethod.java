package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantDeploymentMethod {
    Uni<ViewTenantDeploymentsResponse> execute(ViewTenantDeploymentsRequest request);
}
