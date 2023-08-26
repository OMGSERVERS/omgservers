package com.omgservers.module.tenant.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
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
    Uni<GetTenantResponse> getTenant(GetTenantShardRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantShardRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request);

    default SyncTenantResponse syncTenant(long timeout, SyncTenantShardRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantShardRequest request);

    default void deleteTenant(long timeout, DeleteTenantShardRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request);

    default HasTenantPermissionResponse hasTenantPermission(long timeout, HasTenantPermissionShardRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request);

    default SyncTenantPermissionResponse syncTenantPermission(long timeout, SyncTenantPermissionShardRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request);

    default GetProjectInternalResponse getProject(long timeout, GetProjectShardRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request);

    default SyncProjectInternalResponse syncProject(long timeout, SyncProjectShardRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectShardRequest request);

    default void deleteProject(long timeout, DeleteProjectShardRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request);

    default HasProjectPermissionInternalResponse hasProjectPermission(long timeout, HasProjectPermissionShardRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request);

    default SyncProjectPermissionInternalResponse syncProjectPermission(long timeout, SyncProjectPermissionShardRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageInternalResponse> getStage(GetStageShardRequest request);

    default GetStageInternalResponse getStage(long timeout, GetStageShardRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request);

    default SyncStageInternalResponse syncStage(long timeout, SyncStageShardRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request);

    default DeleteStageInternalResponse deleteStage(long timeout, DeleteStageShardRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request);

    default HasStagePermissionInternalResponse hasStagePermission(long timeout, HasStagePermissionShardRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request);

    default SyncStagePermissionInternalResponse syncStagePermission(long timeout, SyncStagePermissionShardRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
