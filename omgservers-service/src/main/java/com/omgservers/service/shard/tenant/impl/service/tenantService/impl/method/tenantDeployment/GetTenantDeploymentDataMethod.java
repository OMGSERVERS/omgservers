package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentDataMethod {
    Uni<GetTenantDeploymentDataResponse> execute(GetTenantDeploymentDataRequest request);
}
