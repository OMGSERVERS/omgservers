package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion;

import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);
}
