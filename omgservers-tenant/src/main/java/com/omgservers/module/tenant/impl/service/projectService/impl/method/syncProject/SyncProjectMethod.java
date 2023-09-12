package com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProject;

import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);
}
