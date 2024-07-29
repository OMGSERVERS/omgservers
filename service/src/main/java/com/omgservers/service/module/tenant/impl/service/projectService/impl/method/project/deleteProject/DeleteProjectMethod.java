package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.deleteProject;

import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.schema.module.tenant.DeleteProjectResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<DeleteProjectResponse> deleteProject(DeleteProjectRequest request);
}
