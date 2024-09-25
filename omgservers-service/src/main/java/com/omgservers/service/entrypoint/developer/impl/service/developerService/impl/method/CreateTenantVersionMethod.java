package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantVersionMethod {
    Uni<CreateTenantVersionDeveloperResponse> execute(CreateTenantVersionDeveloperRequest request);
}
