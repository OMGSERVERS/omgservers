package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectMethod {
    Uni<CreateTenantProjectDeveloperResponse> execute(CreateTenantProjectDeveloperRequest request);
}
