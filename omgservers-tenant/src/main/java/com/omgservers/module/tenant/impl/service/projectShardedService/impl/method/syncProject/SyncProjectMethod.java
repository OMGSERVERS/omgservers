package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProject;

import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request);
}
