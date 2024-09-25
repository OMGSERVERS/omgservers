package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectMethod {
    Uni<DeleteTenantProjectDeveloperResponse> execute(DeleteTenantProjectDeveloperRequest request);
}
