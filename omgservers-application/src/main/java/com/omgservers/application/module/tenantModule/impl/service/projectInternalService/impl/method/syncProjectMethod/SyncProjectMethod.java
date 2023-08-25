package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod;

import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);
}
