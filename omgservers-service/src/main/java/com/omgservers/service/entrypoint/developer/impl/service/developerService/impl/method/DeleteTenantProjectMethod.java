package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectMethod {
    Uni<DeleteProjectDeveloperResponse> execute(DeleteProjectDeveloperRequest request);
}
