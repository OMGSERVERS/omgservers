package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantImageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantImageMethod {
    Uni<CreateTenantImageDeveloperResponse> execute(CreateTenantImageDeveloperRequest request);
}
