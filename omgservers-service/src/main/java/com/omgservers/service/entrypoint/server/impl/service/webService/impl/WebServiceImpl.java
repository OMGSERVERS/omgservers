package com.omgservers.service.entrypoint.server.impl.service.webService.impl;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
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
    public Uni<PingServerAdminResponse> pingServer() {
        return serverService.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return serverService.generateId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(final BcryptHashAdminRequest request) {
        return serverService.bcryptHash(request);
    }
}
