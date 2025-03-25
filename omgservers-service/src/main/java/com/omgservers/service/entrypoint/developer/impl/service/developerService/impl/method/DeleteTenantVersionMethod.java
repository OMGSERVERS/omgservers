package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantVersionMethod {
    Uni<DeleteVersionDeveloperResponse> execute(DeleteVersionDeveloperRequest request);
}
