package com.omgservers.service.entrypoint.server.impl.service.serverService.impl;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
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
    public Uni<PingServerAdminResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return generateIdMethod.getId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(@Valid final BcryptHashAdminRequest request) {
        return Uni.createFrom().item(BcryptUtil.bcryptHash(request.getValue()))
                .map(BcryptHashAdminResponse::new);
    }
}
