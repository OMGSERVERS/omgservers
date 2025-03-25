package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageMethod {
    Uni<DeleteStageDeveloperResponse> execute(DeleteStageDeveloperRequest request);
}
