package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantVersionMethod {
    Uni<CreateVersionDeveloperResponse> execute(CreateVersionDeveloperRequest request);
}
