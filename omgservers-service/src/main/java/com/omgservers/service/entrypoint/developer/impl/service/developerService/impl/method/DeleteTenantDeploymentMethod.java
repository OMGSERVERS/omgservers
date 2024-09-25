package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentMethod {
    Uni<DeleteTenantDeploymentDeveloperResponse> execute(DeleteTenantDeploymentDeveloperRequest request);
}
