package com.omgservers.service.module.admin.impl.service.webService;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteIndexAdminRequest;
import com.omgservers.model.dto.admin.DeleteIndexAdminResponse;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.admin.FindServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.FindServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.GetServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GetServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface WebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<FindIndexAdminResponse> findIndex(@Valid FindIndexAdminRequest request);

    Uni<SyncIndexAdminResponse> syncIndex(@Valid SyncIndexAdminRequest request);

    Uni<DeleteIndexAdminResponse> deleteIndex(@Valid DeleteIndexAdminRequest request);

    Uni<GetServiceAccountAdminResponse> getServiceAccount(@Valid GetServiceAccountAdminRequest request);

    Uni<FindServiceAccountAdminResponse> findServiceAccount(@Valid FindServiceAccountAdminRequest request);

    Uni<SyncServiceAccountAdminResponse> syncServiceAccount(@Valid SyncServiceAccountAdminRequest request);

    Uni<DeleteServiceAccountAdminResponse> deleteServiceAccount(@Valid DeleteServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
