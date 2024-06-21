package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.deleteProject;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<DeleteProjectResponse> deleteProject(DeleteProjectRequest request);
}
