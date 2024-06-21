package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.pingServer;

import com.omgservers.model.dto.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PingServerMethodImpl implements PingServerMethod {

    @Override
    public Uni<PingServerServerResponse> pingServer() {
        log.debug("Ping server");
        return Uni.createFrom().item(new PingServerServerResponse("PONG"));
    }
}
