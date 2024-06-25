package com.omgservers.service.entrypoint.support.impl.service.webService.impl;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.webService.WebService;
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

    final SupportService supportService;

    @Override
    public Uni<CreateTokenSupportResponse> createToken(final CreateTokenSupportRequest request) {
        return supportService.createToken(request);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(final CreateTenantSupportRequest request) {
        return supportService.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(final DeleteTenantSupportRequest request) {
        return supportService.deleteTenant(request);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(final CreateDeveloperSupportRequest request) {
        return supportService.createDeveloper(request);
    }

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            final CreateTenantPermissionsSupportRequest request) {
        return supportService.createTenantPermissions(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            final DeleteTenantPermissionsSupportRequest request) {
        return supportService.deleteTenantPermissions(request);
    }
}
