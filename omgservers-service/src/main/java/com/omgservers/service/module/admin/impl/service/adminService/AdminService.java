package com.omgservers.service.module.admin.impl.service.adminService;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AdminService {
    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<CreateIndexAdminResponse> createIndex(@Valid CreateIndexAdminRequest request);

    Uni<CreateServiceAccountAdminResponse> createServiceAccount(@Valid CreateServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(@Valid CreateTenantAdminRequest request);

    Uni<DeleteTenantAdminResponse> deleteTenant(@Valid DeleteTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(@Valid CreateDeveloperAdminRequest request);

    Uni<CollectLogsAdminResponse> collectLogs(@Valid CollectLogsAdminRequest request);
}
