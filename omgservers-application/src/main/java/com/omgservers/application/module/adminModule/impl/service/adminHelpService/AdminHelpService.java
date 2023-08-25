package com.omgservers.application.module.adminModule.impl.service.adminHelpService;

import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
import com.omgservers.dto.adminModule.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;

public interface AdminHelpService {
    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(final CreateDeveloperAdminRequest request);

    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
