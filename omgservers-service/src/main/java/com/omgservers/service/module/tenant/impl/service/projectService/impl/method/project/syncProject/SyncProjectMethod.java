package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.syncProject;

import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);
}
