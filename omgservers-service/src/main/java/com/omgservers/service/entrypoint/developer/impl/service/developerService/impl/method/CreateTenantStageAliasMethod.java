package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageAliasMethod {
    Uni<CreateStageAliasDeveloperResponse> execute(CreateStageAliasDeveloperRequest request);
}
