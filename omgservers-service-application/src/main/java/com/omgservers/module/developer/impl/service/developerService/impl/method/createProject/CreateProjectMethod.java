package com.omgservers.module.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);
}
