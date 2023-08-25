package com.omgservers.platforms.integrationtest.operations.getTenantServiceApiClientOperation;

import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
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
    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantInternalRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);

    default SyncTenantResponse syncTenant(long timeout, SyncTenantInternalRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantInternalRequest request);

    default void deleteTenant(long timeout, DeleteTenantInternalRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);

    default HasTenantPermissionResponse hasTenantPermission(long timeout, HasTenantPermissionInternalRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request);

    default SyncTenantPermissionResponse syncTenantPermission(long timeout, SyncTenantPermissionInternalRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);

    default GetProjectInternalResponse getProject(long timeout, GetProjectInternalRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);

    default SyncProjectInternalResponse syncProject(long timeout, SyncProjectInternalRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectInternalRequest request);

    default void deleteProject(long timeout, DeleteProjectInternalRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);

    default HasProjectPermissionInternalResponse hasProjectPermission(long timeout, HasProjectPermissionInternalRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);

    default SyncProjectPermissionInternalResponse syncProjectPermission(long timeout, SyncProjectPermissionInternalRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    default GetStageInternalResponse getStage(long timeout, GetStageInternalRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);

    default SyncStageInternalResponse syncStage(long timeout, SyncStageInternalRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);

    default DeleteStageInternalResponse deleteStage(long timeout, DeleteStageInternalRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    default HasStagePermissionInternalResponse hasStagePermission(long timeout, HasStagePermissionInternalRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);

    default SyncStagePermissionInternalResponse syncStagePermission(long timeout, SyncStagePermissionInternalRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
