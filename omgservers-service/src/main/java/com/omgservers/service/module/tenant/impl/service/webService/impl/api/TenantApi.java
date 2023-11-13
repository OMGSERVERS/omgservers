package com.omgservers.service.module.tenant.impl.service.webService.impl.api;

import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import com.omgservers.model.dto.tenant.DeleteStagePermissionRequest;
import com.omgservers.model.dto.tenant.DeleteStagePermissionResponse;
import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.model.dto.tenant.ViewStagePermissionsRequest;
import com.omgservers.model.dto.tenant.ViewStagePermissionsResponse;
import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/tenant-api/v1/request")
public interface TenantApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);

    @PUT
    @Path("/view-tenant-permission")
    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(ViewTenantPermissionsRequest request);

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    @PUT
    @Path("/delete-tenant-permission")
    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);

    @PUT
    @Path("/get-project")
    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);

    @PUT
    @Path("/view-projects")
    Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request);

    @PUT
    @Path("/delete-project")
    Uni<DeleteProjectResponse> deleteProject(DeleteProjectRequest request);

    @PUT
    @Path("/view-project-permission")
    Uni<ViewProjectPermissionsResponse> viewProjectPermissions(ViewProjectPermissionsRequest request);

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);

    @PUT
    @Path("/delete-project-permission")
    Uni<DeleteProjectPermissionResponse> deleteProjectPermission(DeleteProjectPermissionRequest request);

    @PUT
    @Path("/get-stage")
    Uni<GetStageResponse> getStage(GetStageRequest request);

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);

    @PUT
    @Path("/view-stages")
    Uni<ViewStagesResponse> viewStages(ViewStagesRequest request);

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);

    @PUT
    @Path("/view-stage-permission")
    Uni<ViewStagePermissionsResponse> viewStagePermissions(ViewStagePermissionsRequest request);

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);

    @PUT
    @Path("/delete-stage-permission")
    Uni<DeleteStagePermissionResponse> deleteStagePermission(DeleteStagePermissionRequest request);

    @PUT
    @Path("/get-version")
    Uni<GetVersionResponse> getVersion(GetVersionRequest request);

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);

    @PUT
    @Path("/view-versions")
    Uni<ViewVersionsResponse> viewVersions(ViewVersionsRequest request);

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);

    @PUT
    @Path("/get-version-config")
    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    @PUT
    @Path("/get-version-matchmaker")
    Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(GetVersionMatchmakerRequest request);

    @PUT
    @Path("/sync-version-matchmaker")
    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);

    @PUT
    @Path("/find-version-matchmaker")
    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(FindVersionMatchmakerRequest request);

    @PUT
    @Path("/select-version-matchmaker")
    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(SelectVersionMatchmakerRequest request);

    @PUT
    @Path("/view-version-matchmakers")
    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);

    @PUT
    @Path("/delete-version-matchmaker")
    Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(DeleteVersionMatchmakerRequest request);

    @PUT
    @Path("/get-version-runtime")
    Uni<GetVersionRuntimeResponse> getVersionRuntime(GetVersionRuntimeRequest request);

    @PUT
    @Path("/sync-version-runtime")
    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(SyncVersionRuntimeRequest request);

    @PUT
    @Path("/find-version-runtime")
    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);

    @PUT
    @Path("/select-version-runtime")
    Uni<SelectVersionRuntimeResponse> selectVersionRuntime(SelectVersionRuntimeRequest request);

    @PUT
    @Path("/view-version-runtimes")
    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(ViewVersionRuntimesRequest request);

    @PUT
    @Path("/delete-version-runtime")
    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);

    @PUT
    @Path("/find-stage-version-id")
    Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request);
}
