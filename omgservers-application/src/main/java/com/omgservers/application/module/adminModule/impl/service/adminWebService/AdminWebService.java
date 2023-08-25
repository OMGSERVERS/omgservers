package com.omgservers.application.module.adminModule.impl.service.adminWebService;

import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import com.omgservers.dto.adminModule.DeleteIndexAdminRequest;
import com.omgservers.dto.adminModule.DeleteServiceAccountAdminRequest;
import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
import com.omgservers.dto.adminModule.GetIndexAdminRequest;
import com.omgservers.dto.adminModule.GetIndexAdminResponse;
import com.omgservers.dto.adminModule.GetServiceAccountAdminRequest;
import com.omgservers.dto.adminModule.GetServiceAccountAdminResponse;
import com.omgservers.dto.adminModule.PingServerAdminResponse;
import com.omgservers.dto.adminModule.SyncIndexAdminRequest;
import com.omgservers.dto.adminModule.SyncServiceAccountAdminRequest;
import io.smallrye.mutiny.Uni;

public interface AdminWebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<GetIndexAdminResponse> getIndex(GetIndexAdminRequest request);

    Uni<Void> syncIndex(SyncIndexAdminRequest request);

    Uni<Void> deleteIndex(DeleteIndexAdminRequest request);

    Uni<GetServiceAccountAdminResponse> getServiceAccount(GetServiceAccountAdminRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountAdminRequest request);

    Uni<Void> deleteServiceAccount(DeleteServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
