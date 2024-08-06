package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);
}
