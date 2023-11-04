package com.omgservers.module.admin.impl.service.adminService.impl.method.pingServer;

import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;

public interface PingServerMethod {
    Uni<PingServerAdminResponse> pingServer();
}
