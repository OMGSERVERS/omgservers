package com.omgservers.application.module.adminModule.impl.service.adminHelpService;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface AdminHelpService {
    Uni<PingServerHelpResponse> pingServer();

    Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request);

    Uni<CreateDeveloperHelpResponse> createDeveloper(final CreateDeveloperHelpRequest request);

    Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request);
}
