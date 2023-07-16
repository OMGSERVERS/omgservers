package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request);
}
