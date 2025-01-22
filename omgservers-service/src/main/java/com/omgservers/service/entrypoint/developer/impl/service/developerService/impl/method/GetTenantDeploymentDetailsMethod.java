package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentDetailsMethod {
    Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(GetTenantDeploymentDetailsDeveloperRequest request);
}
