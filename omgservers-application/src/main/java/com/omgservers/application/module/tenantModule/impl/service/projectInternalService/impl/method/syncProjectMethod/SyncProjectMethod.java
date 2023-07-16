package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectMethod {
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);
}
