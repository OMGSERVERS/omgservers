package com.omgservers.service.entrypoint.server.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.server.BcryptHashServerRequest;
import com.omgservers.schema.entrypoint.server.BcryptHashServerResponse;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import com.omgservers.service.entrypoint.server.impl.service.serverService.ServerService;
import com.omgservers.service.entrypoint.server.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final ServerService serverService;

    @Override
    public Uni<PingServerServerResponse> pingServer() {
        return serverService.pingServer();
    }

    @Override
    public Uni<GenerateIdServerResponse> generateId() {
        return serverService.generateId();
    }

    @Override
    public Uni<BcryptHashServerResponse> bcryptHash(final BcryptHashServerRequest request) {
        return serverService.bcryptHash(request);
    }
}
