package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.PingDockerHostMethod;
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

    final PingDockerHostMethod pingDockerHostMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenAdminResponse> execute(final @Valid CreateTokenAdminRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<PingDockerHostAdminResponse> execute(final @Valid PingDockerHostAdminRequest request) {
        return pingDockerHostMethod.execute(request);
    }
}
