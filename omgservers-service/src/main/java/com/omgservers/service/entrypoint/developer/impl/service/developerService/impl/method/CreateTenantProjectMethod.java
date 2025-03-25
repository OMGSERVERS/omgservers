package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectMethod {
    Uni<CreateProjectDeveloperResponse> execute(CreateProjectDeveloperRequest request);
}
