package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantVersionMethod {
    Uni<DeleteTenantVersionDeveloperResponse> execute(DeleteTenantVersionDeveloperRequest request);
}
