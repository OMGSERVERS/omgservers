package com.omgservers.module.tenant.impl.service.projectService.impl.method.deleteProject;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectMethod {
    Uni<Void> deleteProject(DeleteProjectRequest request);
}
