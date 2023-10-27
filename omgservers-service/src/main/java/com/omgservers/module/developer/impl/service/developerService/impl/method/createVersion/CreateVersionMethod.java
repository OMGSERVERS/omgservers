package com.omgservers.module.developer.impl.service.developerService.impl.method.createVersion;

import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);
}
