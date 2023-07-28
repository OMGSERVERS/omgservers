package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request);
}
