package com.omgservers.service.module.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.module.tenant.DeleteProjectPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteProjectPermissionResponse;
import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.schema.module.tenant.DeleteProjectResponse;
import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import com.omgservers.schema.module.tenant.DeleteStageRequest;
import com.omgservers.schema.module.tenant.DeleteStageResponse;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.DeleteVersionRequest;
import com.omgservers.schema.module.tenant.DeleteVersionResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.GetProjectRequest;
import com.omgservers.schema.module.tenant.GetProjectResponse;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.GetTenantDashboardRequest;
import com.omgservers.schema.module.tenant.GetTenantDashboardResponse;
import com.omgservers.schema.module.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.GetVersionConfigRequest;
import com.omgservers.schema.module.tenant.GetVersionConfigResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.HasProjectPermissionRequest;
import com.omgservers.schema.module.tenant.HasProjectPermissionResponse;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import com.omgservers.schema.module.tenant.SelectStageVersionRequest;
import com.omgservers.schema.module.tenant.SelectStageVersionResponse;
import com.omgservers.schema.module.tenant.SyncProjectPermissionRequest;
import com.omgservers.schema.module.tenant.SyncProjectPermissionResponse;
import com.omgservers.schema.module.tenant.SyncProjectRequest;
import com.omgservers.schema.module.tenant.SyncProjectResponse;
import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.schema.module.tenant.SyncStagePermissionResponse;
import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.schema.module.tenant.SyncStageResponse;
import com.omgservers.schema.module.tenant.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.SyncTenantPermissionResponse;
import com.omgservers.schema.module.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.SyncTenantResponse;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.SyncVersionRequest;
import com.omgservers.schema.module.tenant.SyncVersionResponse;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.ViewProjectsRequest;
import com.omgservers.schema.module.tenant.ViewProjectsResponse;
import com.omgservers.schema.module.tenant.ViewStagePermissionsRequest;
import com.omgservers.schema.module.tenant.ViewStagePermissionsResponse;
import com.omgservers.schema.module.tenant.ViewStagesRequest;
import com.omgservers.schema.module.tenant.ViewStagesResponse;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
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
    @Path("/get-version-jenkins-request")
    Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(GetVersionJenkinsRequestRequest request);

    @PUT
    @Path("/view-version-jenkins-requests")
    Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(ViewVersionJenkinsRequestsRequest request);

    @PUT
    @Path("/sync-version-jenkins-request")
    Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(SyncVersionJenkinsRequestRequest request);

    @PUT
    @Path("/delete-version-jenkins-request")
    Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(DeleteVersionJenkinsRequestRequest request);

    @PUT
    @Path("/get-version-image-ref")
    Uni<GetVersionImageRefResponse> getVersionImageRef(GetVersionImageRefRequest request);

    @PUT
    @Path("/find-version-image-ref")
    Uni<FindVersionImageRefResponse> findVersionImageRef(FindVersionImageRefRequest request);

    @PUT
    @Path("/view-version-image-refs")
    Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(ViewVersionImageRefsRequest request);

    @PUT
    @Path("/sync-version-image-ref")
    Uni<SyncVersionImageRefResponse> syncVersionImageRef(SyncVersionImageRefRequest request);

    @PUT
    @Path("/delete-version-image-ref")
    Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(DeleteVersionImageRefRequest request);

    @PUT
    @Path("/get-version-lobby-request")
    Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(GetVersionLobbyRequestRequest request);

    @PUT
    @Path("/find-version-lobby-request")
    Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(FindVersionLobbyRequestRequest request);

    @PUT
    @Path("/view-version-lobby-requests")
    Uni<ViewVersionLobbyRequestsResponse> viewVersionJenkinsRequests(ViewVersionLobbyRequestsRequest request);

    @PUT
    @Path("/sync-version-lobby-request")
    Uni<SyncVersionLobbyRequestResponse> syncVersionJenkinsRequest(SyncVersionLobbyRequestRequest request);

    @PUT
    @Path("/delete-version-lobby-request")
    Uni<DeleteVersionLobbyRequestResponse> deleteVersionJenkinsRequest(DeleteVersionLobbyRequestRequest request);

    @PUT
    @Path("/get-version-lobby-ref")
    Uni<GetVersionLobbyRefResponse> getVersionImageRef(GetVersionLobbyRefRequest request);

    @PUT
    @Path("/find-version-lobby-ref")
    Uni<FindVersionLobbyRefResponse> findVersionImageRef(FindVersionLobbyRefRequest request);

    @PUT
    @Path("/view-version-lobby-refs")
    Uni<ViewVersionLobbyRefsResponse> viewVersionImageRefs(ViewVersionLobbyRefsRequest request);

    @PUT
    @Path("/sync-version-lobby-ref")
    Uni<SyncVersionLobbyRefResponse> syncVersionImageRef(SyncVersionLobbyRefRequest request);

    @PUT
    @Path("/delete-version-lobby-ref")
    Uni<DeleteVersionLobbyRefResponse> deleteVersionImageRef(DeleteVersionLobbyRefRequest request);

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
