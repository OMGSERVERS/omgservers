package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMethod {
    Uni<DeleteDeploymentDeveloperResponse> execute(DeleteDeploymentDeveloperRequest request);
}
