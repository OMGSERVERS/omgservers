package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeploymentMethod {
    Uni<CreateDeploymentDeveloperResponse> execute(CreateDeploymentDeveloperRequest request);
}
