package com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProject;

import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectShardedResponse> syncProject(SyncProjectRequest request);
}
