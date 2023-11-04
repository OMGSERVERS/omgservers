package com.omgservers.module.admin.impl.service.webService;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteIndexAdminRequest;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.GetIndexAdminRequest;
import com.omgservers.model.dto.admin.GetIndexAdminResponse;
import com.omgservers.model.dto.admin.GetServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GetServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import io.smallrye.mutiny.Uni;

public interface WebService {

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
