package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.pingServer;

import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;

public interface PingServerMethod {
    Uni<PingServerAdminResponse> pingServer();
}
