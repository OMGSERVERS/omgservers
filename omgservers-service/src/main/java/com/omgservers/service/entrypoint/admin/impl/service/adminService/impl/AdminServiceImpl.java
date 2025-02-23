package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminRequest;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.BcryptHashMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.CalculateShardMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.GenerateIdMethod;
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
    final CalculateShardMethod calculateShardMethod;
    final CreateTokenMethod createTokenMethod;
    final GenerateIdMethod generateIdMethod;
    final BcryptHashMethod bcryptHashMethod;

    @Override
    public Uni<CreateTokenAdminResponse> execute(@Valid final CreateTokenAdminRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<GenerateIdAdminResponse> execute(@Valid final GenerateIdAdminRequest request) {
        return generateIdMethod.execute(request);
    }

    @Override
    public Uni<BcryptHashAdminResponse> execute(@Valid final BcryptHashAdminRequest request) {
        return bcryptHashMethod.execute(request);
    }

    @Override
    public Uni<CalculateShardAdminResponse> execute(@Valid final CalculateShardAdminRequest request) {
        return calculateShardMethod.execute(request);
    }

    @Override
    public Uni<PingDockerHostAdminResponse> execute(@Valid final PingDockerHostAdminRequest request) {
        return pingDockerHostMethod.execute(request);
    }
}
