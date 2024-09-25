package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageMethod {
    Uni<CreateTenantStageDeveloperResponse> execute(CreateTenantStageDeveloperRequest request);
}
