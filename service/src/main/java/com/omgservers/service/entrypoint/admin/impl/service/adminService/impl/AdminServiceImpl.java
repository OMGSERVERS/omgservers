package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
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

    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenAdminResponse> createToken(final @Valid CreateTokenAdminRequest request) {
        return createTokenMethod.createToken(request);
    }
}
