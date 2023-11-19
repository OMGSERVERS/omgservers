package com.omgservers.service.module.admin.impl.service.webService.impl.adminApi;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/admin-api/v1/request")
public interface AdminApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerAdminResponse> pingServer();

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> generateId();

    @PUT
    @Path("/create-index")
    Uni<CreateIndexAdminResponse> createIndex(CreateIndexAdminRequest request);

    @PUT
    @Path("/create-service-account")
    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request);

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    @PUT
    @Path("/collect-logs")
    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);

}
