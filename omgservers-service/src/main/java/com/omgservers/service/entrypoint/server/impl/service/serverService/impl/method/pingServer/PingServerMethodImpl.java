package com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.pingServer;

import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class PingServerMethodImpl implements PingServerMethod {

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<PingServerServerResponse> pingServer() {
        log.info("Ping server, principal={}", securityIdentity.getPrincipal().getName());

        return Uni.createFrom().item(new PingServerServerResponse("PONG"));
    }
}
