package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionMethod;

import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);
}
