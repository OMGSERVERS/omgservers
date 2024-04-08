package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.bootstrapIndex.BootstrapIndexMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.generateId.GenerateIdMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.pingServer.PingServerMethod;
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
class AdminServiceImpl implements AdminService {

    final BootstrapIndexMethod bootstrapIndexMethod;
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

    @Override
    public Uni<BootstrapIndexServerResponse> bootstrapIndex(@Valid final BootstrapIndexServerRequest request) {
        return bootstrapIndexMethod.bootstrapIndex(request);
    }
}
