package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.syncIndexMethod;

import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexAdminResponse> syncIndex(SyncIndexAdminRequest request);
}
