package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.getProject;

import com.omgservers.schema.module.tenant.GetProjectRequest;
import com.omgservers.schema.module.tenant.GetProjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectResponse> getProject(GetProjectRequest request);
}
