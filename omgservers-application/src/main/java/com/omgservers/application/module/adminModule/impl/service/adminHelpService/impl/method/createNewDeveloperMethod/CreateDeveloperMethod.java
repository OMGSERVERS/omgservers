package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod;

import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDeveloperMethod {
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);
}
