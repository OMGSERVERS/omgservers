package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantImageMethod {
    Uni<CreateImageDeveloperResponse> execute(CreateImageDeveloperRequest request);
}
