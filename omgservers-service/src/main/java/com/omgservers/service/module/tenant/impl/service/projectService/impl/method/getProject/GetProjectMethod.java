package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.getProject;

import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetProjectMethod {
    Uni<GetProjectResponse> getProject(GetProjectRequest request);
}
