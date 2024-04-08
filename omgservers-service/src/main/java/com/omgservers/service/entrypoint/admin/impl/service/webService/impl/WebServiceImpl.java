package com.omgservers.service.entrypoint.admin.impl.service.webService.impl;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.webService.WebService;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final SystemModule systemModule;
    final AdminService adminService;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return adminService.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return adminService.generateId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(final BcryptHashAdminRequest request) {
        return adminService.bcryptHash(request);
    }

    @Override
    public Uni<BootstrapIndexServerResponse> bootstrapIndex(final BootstrapIndexServerRequest request) {
        return adminService.bootstrapIndex(request);
    }
}
