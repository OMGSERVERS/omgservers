package com.omgservers.service.entrypoint.support.impl.service.supportService.impl;

import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportRequest;
import com.omgservers.model.dto.support.CreateDefaultPoolServerSupportResponse;
import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDefaultPoolServer.CreateDefaultPoolServerMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDeveloper.CreateDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenant.CreateTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenant.DeleteTenantMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportServiceImpl implements SupportService {

    final CreateDefaultPoolServerMethod createDefaultPoolServerMethod;
    final CreateDeveloperMethod createDeveloperMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;

    @Override
    public Uni<CreateDefaultPoolServerSupportResponse> createDefaultPoolServer(
            @Valid final CreateDefaultPoolServerSupportRequest request) {
        return createDefaultPoolServerMethod.createDefaultPoolServer(request);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(@Valid final CreateTenantSupportRequest request) {
        return createTenantMethod.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(@Valid final DeleteTenantSupportRequest request) {
        return deleteTenantMethod.deleteTenant(request);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid final CreateDeveloperSupportRequest request) {
        return createDeveloperMethod.createDeveloper(request);
    }
}