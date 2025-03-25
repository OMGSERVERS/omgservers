package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageMethod {
    Uni<CreateStageDeveloperResponse> execute(CreateStageDeveloperRequest request);
}
