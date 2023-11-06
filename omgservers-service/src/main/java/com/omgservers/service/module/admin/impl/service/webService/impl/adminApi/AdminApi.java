package com.omgservers.service.module.admin.impl.service.webService.impl.adminApi;

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
    @Path("/find-index")
    Uni<FindIndexAdminResponse> findIndex(FindIndexAdminRequest request);

    @PUT
    @Path("/sync-index")
    Uni<SyncIndexAdminResponse> syncIndex(SyncIndexAdminRequest request);

    @PUT
    @Path("/delete-index")
    Uni<DeleteIndexAdminResponse> deleteIndex(DeleteIndexAdminRequest request);

    @PUT
    @Path("/get-service-account")
    Uni<GetServiceAccountAdminResponse> getServiceAccount(GetServiceAccountAdminRequest request);

    @PUT
    @Path("/find-service-account")
    Uni<FindServiceAccountAdminResponse> findServiceAccount(FindServiceAccountAdminRequest request);

    @PUT
    @Path("/sync-service-account")
    Uni<SyncServiceAccountAdminResponse> syncServiceAccount(SyncServiceAccountAdminRequest request);

    @PUT
    @Path("/delete-service-account")
    Uni<DeleteServiceAccountAdminResponse> deleteServiceAccount(DeleteServiceAccountAdminRequest request);

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    @PUT
    @Path("/collect-logs")
    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);

}
