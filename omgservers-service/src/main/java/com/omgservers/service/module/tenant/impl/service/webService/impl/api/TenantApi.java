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
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.FindVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SelectStageVersionRequest;
import com.omgservers.model.dto.tenant.SelectStageVersionResponse;
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
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
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
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Tenant Module API")
@Path("/omgservers/v1/module/tenant/request")
public interface TenantApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    @PUT
    @Path("/get-tenant-dashboard")
    Uni<GetTenantDashboardResponse> getTenantDashboard(GetTenantDashboardRequest request);

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);

    @PUT
    @Path("/view-tenant-permissions")
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
    @Path("/view-project-permissions")
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
    @Path("/view-stage-permissions")
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
    @Path("/select-stage-version")
    Uni<SelectStageVersionResponse> selectStageVersion(SelectStageVersionRequest request);

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
    @Path("/get-version-lobby-request")
    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(GetVersionLobbyRequestRequest request);

    @PUT
    @Path("/find-version-lobby-request")
    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(FindVersionLobbyRequestRequest request);

    @PUT
    @Path("/view-version-lobby-requests")
    Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(ViewVersionLobbyRequestsRequest request);

    @PUT
    @Path("/sync-version-lobby-request")
    Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(SyncVersionLobbyRequestRequest request);

    @PUT
    @Path("/delete-version-lobby-request")
    Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(DeleteVersionLobbyRequestRequest request);

    @PUT
    @Path("/get-version-lobby-ref")
    Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(GetVersionLobbyRefRequest request);

    @PUT
    @Path("/find-version-lobby-ref")
    Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(FindVersionLobbyRefRequest request);

    @PUT
    @Path("/view-version-lobby-refs")
    Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(ViewVersionLobbyRefsRequest request);

    @PUT
    @Path("/sync-version-lobby-ref")
    Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(SyncVersionLobbyRefRequest request);

    @PUT
    @Path("/delete-version-lobby-ref")
    Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(DeleteVersionLobbyRefRequest request);

    @PUT
    @Path("/get-version-matchmaker-request")
    Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(GetVersionMatchmakerRequestRequest request);

    @PUT
    @Path("/find-version-matchmaker-request")
    Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(FindVersionMatchmakerRequestRequest request);

    @PUT
    @Path("/view-version-matchmaker-requests")
    Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            ViewVersionMatchmakerRequestsRequest request);

    @PUT
    @Path("/sync-version-matchmaker-request")
    Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(SyncVersionMatchmakerRequestRequest request);

    @PUT
    @Path("/delete-version-matchmaker-request")
    Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            DeleteVersionMatchmakerRequestRequest request);

    @PUT
    @Path("/get-version-matchmaker-ref")
    Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(GetVersionMatchmakerRefRequest request);

    @PUT
    @Path("/find-version-matchmaker-ref")
    Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(FindVersionMatchmakerRefRequest request);

    @PUT
    @Path("/view-version-matchmaker-refs")
    Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(ViewVersionMatchmakerRefsRequest request);

    @PUT
    @Path("/sync-version-matchmaker-ref")
    Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(SyncVersionMatchmakerRefRequest request);

    @PUT
    @Path("/delete-version-matchmaker-ref")
    Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(DeleteVersionMatchmakerRefRequest request);
}
