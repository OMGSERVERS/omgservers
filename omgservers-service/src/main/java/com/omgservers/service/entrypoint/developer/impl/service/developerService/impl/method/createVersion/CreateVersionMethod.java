package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion;

import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);
}
