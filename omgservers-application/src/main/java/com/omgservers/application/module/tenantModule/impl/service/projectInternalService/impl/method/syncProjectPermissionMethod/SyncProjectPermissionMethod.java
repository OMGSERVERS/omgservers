package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectPermissionMethod;

import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncProjectPermissionMethod {
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);
}
