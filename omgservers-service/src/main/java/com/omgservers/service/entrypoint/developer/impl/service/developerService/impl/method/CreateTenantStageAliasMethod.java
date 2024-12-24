package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageAliasMethod {
    Uni<CreateTenantStageAliasDeveloperResponse> execute(CreateTenantStageAliasDeveloperRequest request);
}
