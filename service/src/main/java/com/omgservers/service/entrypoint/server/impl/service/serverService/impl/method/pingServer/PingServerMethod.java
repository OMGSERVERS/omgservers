package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.pingServer;

import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;

public interface PingServerMethod {
    Uni<PingServerServerResponse> pingServer();
}
