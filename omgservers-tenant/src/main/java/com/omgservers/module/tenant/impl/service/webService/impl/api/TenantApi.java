package com.omgservers.module.tenant.impl.service.webService.impl.api;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/tenant-api/v1/request")
public interface TenantApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    default SyncTenantResponse syncTenant(long timeout, SyncTenantRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantRequest request);

    default void deleteTenant(long timeout, DeleteTenantRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    default HasTenantPermissionResponse hasTenantPermission(long timeout, HasTenantPermissionRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    default SyncTenantPermissionResponse syncTenantPermission(long timeout, SyncTenantPermissionRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    default GetProjectResponse getProject(long timeout, GetProjectRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);

    default SyncProjectResponse syncProject(long timeout, SyncProjectRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectRequest request);

    default void deleteProject(long timeout, DeleteProjectRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);

    default HasProjectPermissionResponse hasProjectPermission(long timeout, HasProjectPermissionRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);

    default SyncProjectPermissionResponse syncProjectPermission(long timeout, SyncProjectPermissionRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageResponse> getStage(GetStageRequest request);

    default GetStageResponse getStage(long timeout, GetStageRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);

    default SyncStageResponse syncStage(long timeout, SyncStageRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);

    default DeleteStageResponse deleteStage(long timeout, DeleteStageRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);

    default HasStagePermissionResponse hasStagePermission(long timeout, HasStagePermissionRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);

    default SyncStagePermissionResponse syncStagePermission(long timeout, SyncStagePermissionRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-version")
    Uni<GetVersionResponse> getVersion(GetVersionRequest request);

    default GetVersionResponse getVersion(long timeout, GetVersionRequest request) {
        return getVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);

    default SyncVersionResponse syncVersion(long timeout, SyncVersionRequest request) {
        return syncVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);

    default void deleteVersion(long timeout, DeleteVersionRequest request) {
        deleteVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-version-bytecode")
    Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request);

    default GetVersionBytecodeResponse getVersionBytecode(long timeout, GetVersionBytecodeRequest request) {
        return getVersionBytecode(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-version-config")
    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    default GetVersionConfigResponse getVersionConfig(long timeout, GetVersionConfigRequest request) {
        return getVersionConfig(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage-version-id")
    Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request);

    default GetStageVersionIdResponse getStageVersionId(long timeout, GetStageVersionIdRequest request) {
        return getStageVersionId(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
