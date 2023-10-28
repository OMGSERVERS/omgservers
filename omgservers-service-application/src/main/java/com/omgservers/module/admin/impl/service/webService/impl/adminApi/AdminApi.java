package com.omgservers.module.admin.impl.service.webService.impl.adminApi;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/admin-api/v1/request")
public interface AdminApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerAdminResponse> pingServer();

    default PingServerAdminResponse pingServer(long timeout) {
        return pingServer()
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> generateId();

    default GenerateIdAdminResponse generateId(long timeout) {
        return generateId()
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-index")
    Uni<GetIndexAdminResponse> getIndex(GetIndexAdminRequest request);

    default GetIndexAdminResponse getIndex(long timeout, GetIndexAdminRequest request) {
        return getIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-index")
    Uni<Void> syncIndex(SyncIndexAdminRequest request);

    default void syncIndex(long timeout, SyncIndexAdminRequest request) {
        syncIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-index")
    Uni<Void> deleteIndex(DeleteIndexAdminRequest request);

    default void deleteIndex(long timeout, DeleteIndexAdminRequest request) {
        deleteIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-service-account")
    Uni<GetServiceAccountAdminResponse> getServiceAccount(GetServiceAccountAdminRequest request);

    default GetServiceAccountAdminResponse getServiceAccount(long timeout, GetServiceAccountAdminRequest request) {
        return getServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-service-account")
    Uni<Void> syncServiceAccount(SyncServiceAccountAdminRequest request);

    default void syncServiceAccount(long timeout, SyncServiceAccountAdminRequest request) {
        syncServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-service-account")
    Uni<Void> deleteServiceAccount(DeleteServiceAccountAdminRequest request);

    default void deleteServiceAccount(long timeout, DeleteServiceAccountAdminRequest request) {
        deleteServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    default CreateTenantAdminResponse createTenant(long timeout, CreateTenantAdminRequest request) {
        return createTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

    default CreateDeveloperAdminResponse createDeveloper(long timeout, CreateDeveloperAdminRequest request) {
        return createDeveloper(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/collect-logs")
    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);

    default CollectLogsAdminResponse collectLogs(long timeout, CollectLogsAdminRequest request) {
        return collectLogs(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
