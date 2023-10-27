package com.omgservers.module.tenant.impl.service.projectService.impl.method.getProject;

import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectResponse> getProject(GetProjectRequest request);
}
