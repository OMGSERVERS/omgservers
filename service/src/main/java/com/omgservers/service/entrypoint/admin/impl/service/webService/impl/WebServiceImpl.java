package com.omgservers.service.entrypoint.admin.impl.service.webService.impl;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
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
    public Uni<CreateTokenAdminResponse> createToken(final CreateTokenAdminRequest request) {
        return adminService.createToken(request);
    }
}
