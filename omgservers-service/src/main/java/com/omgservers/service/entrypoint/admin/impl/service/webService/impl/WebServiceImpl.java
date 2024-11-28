package com.omgservers.service.entrypoint.admin.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final AdminService adminService;

    @Override
    public Uni<CreateTokenAdminResponse> execute(final CreateTokenAdminRequest request) {
        return adminService.execute(request);
    }

    @Override
    public Uni<PingDockerHostAdminResponse> execute(final PingDockerHostAdminRequest request) {
        return adminService.execute(request);
    }
}
