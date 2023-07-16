package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.createProjectMethod;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.CreateProjectInternalRequest;
import io.smallrye.mutiny.Uni;

public interface CreateProjectMethod {
    Uni<Void> createProject(CreateProjectInternalRequest request);
}
