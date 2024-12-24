package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectAliasMethod {
    Uni<CreateTenantProjectAliasDeveloperResponse> execute(CreateTenantProjectAliasDeveloperRequest request);
}
