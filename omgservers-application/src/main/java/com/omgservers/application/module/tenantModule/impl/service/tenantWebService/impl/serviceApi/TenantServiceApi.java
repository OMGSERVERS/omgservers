package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.dto.tenantModule.DeleteProjectRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/tenant-api/v1/request")
public interface TenantServiceApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantRoutedRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRoutedRequest request);

    default SyncTenantResponse syncTenant(long timeout, SyncTenantRoutedRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantRoutedRequest request);

    default void deleteTenant(long timeout, DeleteTenantRoutedRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request);

    default HasTenantPermissionResponse hasTenantPermission(long timeout, HasTenantPermissionRoutedRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request);

    default SyncTenantPermissionResponse syncTenantPermission(long timeout, SyncTenantPermissionRoutedRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectInternalResponse> getProject(GetProjectRoutedRequest request);

    default GetProjectInternalResponse getProject(long timeout, GetProjectRoutedRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectRoutedRequest request);

    default SyncProjectInternalResponse syncProject(long timeout, SyncProjectRoutedRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectRoutedRequest request);

    default void deleteProject(long timeout, DeleteProjectRoutedRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionRoutedRequest request);

    default HasProjectPermissionInternalResponse hasProjectPermission(long timeout, HasProjectPermissionRoutedRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionRoutedRequest request);

    default SyncProjectPermissionInternalResponse syncProjectPermission(long timeout, SyncProjectPermissionRoutedRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request);

    default GetStageInternalResponse getStage(long timeout, GetStageRoutedRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request);

    default SyncStageInternalResponse syncStage(long timeout, SyncStageRoutedRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request);

    default DeleteStageInternalResponse deleteStage(long timeout, DeleteStageRoutedRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request);

    default HasStagePermissionInternalResponse hasStagePermission(long timeout, HasStagePermissionRoutedRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionRoutedRequest request);

    default SyncStagePermissionInternalResponse syncStagePermission(long timeout, SyncStagePermissionRoutedRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
