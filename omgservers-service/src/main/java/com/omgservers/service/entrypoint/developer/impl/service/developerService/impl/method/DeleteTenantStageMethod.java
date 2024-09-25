package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageMethod {
    Uni<DeleteTenantStageDeveloperResponse> execute(DeleteTenantStageDeveloperRequest request);
}
