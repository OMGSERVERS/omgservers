package com.omgservers.application.module.adminModule.impl.service.adminWebService;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface AdminWebService {

    Uni<PingServerHelpResponse> pingServer();

    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);

    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);

    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request);

    Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request);

    Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request);
}
