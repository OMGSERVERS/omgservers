package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectAliasMethod {
    Uni<CreateProjectAliasDeveloperResponse> execute(CreateProjectAliasDeveloperRequest request);
}
