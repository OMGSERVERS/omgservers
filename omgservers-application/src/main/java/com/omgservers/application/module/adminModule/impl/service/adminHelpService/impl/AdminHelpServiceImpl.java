package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod.PingServerMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.AdminHelpService;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod.CreateDeveloperMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod.CreateTenantMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminHelpServiceImpl implements AdminHelpService {

    final CreateDeveloperMethod createDeveloperAdminMethod;
    final CreateTenantMethod createTenantAdminMethod;
    final PingServerMethod pingServerMethod;

    @Override
    public Uni<PingServerHelpResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request) {
        return createTenantAdminMethod.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request) {
        return createDeveloperAdminMethod.createDeveloper(request);
    }
}
