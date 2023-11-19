package com.omgservers.service.module.admin.impl.service.webService;

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

public interface WebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<CreateIndexAdminResponse> createIndex(CreateIndexAdminRequest request);

    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
