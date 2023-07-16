package com.omgservers.application.module.adminModule.impl.service.adminWebService.impl.adminApi;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/admin-api/v1/request")
public interface AdminApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerHelpResponse> pingServer();

    default PingServerHelpResponse pingServer(long timeout) {
        return pingServer()
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-index")
    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);

    default GetIndexHelpResponse getIndex(long timeout, GetIndexHelpRequest request) {
        return getIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-index")
    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    default void syncIndex(long timeout, SyncIndexHelpRequest request) {
        syncIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-index")
    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);

    default void deleteIndex(long timeout, DeleteIndexHelpRequest request) {
        deleteIndex(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-service-account")
    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);

    default GetServiceAccountHelpResponse getServiceAccount(long timeout, GetServiceAccountHelpRequest request) {
        return getServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-service-account")
    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    default void syncServiceAccount(long timeout, SyncServiceAccountHelpRequest request) {
        syncServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-service-account")
    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);

    default void deleteServiceAccount(long timeout, DeleteServiceAccountHelpRequest request) {
        deleteServiceAccount(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request);

    default CreateTenantHelpResponse createTenant(long timeout, CreateTenantHelpRequest request) {
        return createTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request);

    default CreateDeveloperHelpResponse createDeveloper(long timeout, CreateDeveloperHelpRequest request) {
        return createDeveloper(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
