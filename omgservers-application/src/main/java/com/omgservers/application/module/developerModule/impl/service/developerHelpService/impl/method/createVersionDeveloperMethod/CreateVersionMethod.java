package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionDeveloperMethod;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateVersionMethod {
    Uni<CreateVersionHelpResponse> createVersion(CreateVersionHelpRequest request);
}
