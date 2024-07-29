package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.syncProject;

import com.omgservers.schema.module.tenant.SyncProjectRequest;
import com.omgservers.schema.module.tenant.SyncProjectResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);
}
