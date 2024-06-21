package com.omgservers.service.entrypoint.server.impl.service.serverService.impl;

import com.omgservers.model.dto.server.BcryptHashServerRequest;
import com.omgservers.model.dto.server.BcryptHashServerResponse;
import com.omgservers.model.dto.server.GenerateIdServerResponse;
import com.omgservers.model.dto.server.PingServerServerResponse;
import com.omgservers.service.entrypoint.server.impl.service.serverService.ServerService;
import com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.generateId.GenerateIdMethod;
import com.omgservers.service.entrypoint.server.impl.service.serverService.impl.method.pingServer.PingServerMethod;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ServerServiceImpl implements ServerService {

    final PingServerMethod pingServerMethod;
    final GenerateIdMethod generateIdMethod;

    @Override
    public Uni<PingServerServerResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<GenerateIdServerResponse> generateId() {
        return generateIdMethod.getId();
    }

    @Override
    public Uni<BcryptHashServerResponse> bcryptHash(@Valid final BcryptHashServerRequest request) {
        return Uni.createFrom().item(BcryptUtil.bcryptHash(request.getValue()))
                .map(BcryptHashServerResponse::new);
    }
}
