package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.AdminHelpService;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod.CollectLogsMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod.CreateDeveloperMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod.CreateTenantMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.getIdMethod.GenerateIdMethod;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod.PingServerMethod;
import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
import com.omgservers.dto.adminModule.PingServerAdminResponse;
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
    final CollectLogsMethod collectLogsMethod;
    final PingServerMethod pingServerMethod;
    final GenerateIdMethod generateIdMethod;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return generateIdMethod.getId();
    }

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return createTenantAdminMethod.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return createDeveloperAdminMethod.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return collectLogsMethod.collectLogs(request);
    }
}
