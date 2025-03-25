package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentDetailsMethod {
    Uni<GetDeploymentDetailsDeveloperResponse> execute(GetDeploymentDetailsDeveloperRequest request);
}
