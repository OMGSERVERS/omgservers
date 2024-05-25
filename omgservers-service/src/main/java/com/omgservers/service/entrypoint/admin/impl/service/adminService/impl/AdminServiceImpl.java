package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

import com.omgservers.model.dto.admin.CreateSupportAdminRequest;
import com.omgservers.model.dto.admin.CreateSupportAdminResponse;
import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createSupport.CreateSupportMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createToken.CreateTokenMethod;
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

    final CreateSupportMethod createSupportMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenAdminResponse> createToken(final @Valid CreateTokenAdminRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<CreateSupportAdminResponse> createSupport(final @Valid CreateSupportAdminRequest request) {
        return createSupportMethod.createSupport(request);
    }
}
