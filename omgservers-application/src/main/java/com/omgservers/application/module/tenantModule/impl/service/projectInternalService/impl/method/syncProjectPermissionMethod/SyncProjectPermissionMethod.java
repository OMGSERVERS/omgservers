package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);
}
