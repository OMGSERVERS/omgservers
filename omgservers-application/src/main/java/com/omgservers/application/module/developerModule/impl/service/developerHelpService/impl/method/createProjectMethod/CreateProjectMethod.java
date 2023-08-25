package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod;

import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);
}
