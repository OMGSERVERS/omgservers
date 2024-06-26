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
import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.FindVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.model.dto.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.model.dto.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.tenant.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
class TenantApiImpl implements TenantApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenant);
    }

    @Override
    public Uni<GetTenantDashboardResponse> getTenantDashboard(final GetTenantDashboardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDashboard);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantPermissions);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(final HasTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantPermission);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantPermission);
    }

    @Override
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getProject);
    }

    @Override
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProject);
    }

    @Override
    public Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewProjects);
    }

    @Override
    public Uni<DeleteProjectResponse> deleteProject(final DeleteProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProject);
    }

    @Override
    public Uni<ViewProjectPermissionsResponse> viewProjectPermissions(final ViewProjectPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewProjectPermissions);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(final HasProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(final SyncProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProjectPermission);
    }

    @Override
    public Uni<DeleteProjectPermissionResponse> deleteProjectPermission(final DeleteProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProjectPermission);
    }

    @Override
    public Uni<GetStageResponse> getStage(final GetStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getStage);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStage);
    }

    @Override
    public Uni<ViewStagesResponse> viewStages(ViewStagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewStages);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(final DeleteStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteStage);
    }

    @Override
    public Uni<ViewStagePermissionsResponse> viewStagePermissions(final ViewStagePermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewStagePermissions);
    }

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(final HasStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(final SyncStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStagePermission);
    }

    @Override
    public Uni<DeleteStagePermissionResponse> deleteStagePermission(final DeleteStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteStagePermission);
    }

    @Override
    public Uni<SelectStageVersionResponse> selectStageVersion(final SelectStageVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::selectStageVersion);
    }

    @Override
    public Uni<GetVersionResponse> getVersion(final GetVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersion);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(final SyncVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersion);
    }

    @Override
    public Uni<ViewVersionsResponse> viewVersions(final ViewVersionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersions);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(final DeleteVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersion);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(final GetVersionConfigRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionConfig);
    }

    @Override
    public Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(final GetVersionJenkinsRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionJenkinsRequest);
    }

    @Override
    public Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            final ViewVersionJenkinsRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionJenkinsRequests);
    }

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(final SyncVersionJenkinsRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionJenkinsRequest);
    }

    @Override
    public Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(
            final DeleteVersionJenkinsRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionJenkinsRequest);
    }

    @Override
    public Uni<GetVersionImageRefResponse> getVersionImageRef(final GetVersionImageRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionImageRef);
    }

    @Override
    public Uni<FindVersionImageRefResponse> findVersionImageRef(final FindVersionImageRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionImageRef);
    }

    @Override
    public Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(final ViewVersionImageRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionImageRefs);
    }

    @Override
    public Uni<SyncVersionImageRefResponse> syncVersionImageRef(final SyncVersionImageRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionImageRef);
    }

    @Override
    public Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(final DeleteVersionImageRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionImageRef);
    }

    @Override
    public Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(final GetVersionLobbyRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionLobbyRequest);
    }

    @Override
    public Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(final FindVersionLobbyRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionLobbyRequest);
    }

    @Override
    public Uni<ViewVersionLobbyRequestsResponse> viewVersionJenkinsRequests(
            final ViewVersionLobbyRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionLobbyRequests);
    }

    @Override
    public Uni<SyncVersionLobbyRequestResponse> syncVersionJenkinsRequest(final SyncVersionLobbyRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionLobbyRequest);
    }

    @Override
    public Uni<DeleteVersionLobbyRequestResponse> deleteVersionJenkinsRequest(
            final DeleteVersionLobbyRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionLobbyRequest);
    }

    @Override
    public Uni<GetVersionLobbyRefResponse> getVersionImageRef(final GetVersionLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionLobbyRef);
    }

    @Override
    public Uni<FindVersionLobbyRefResponse> findVersionImageRef(final FindVersionLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionLobbyRef);
    }

    @Override
    public Uni<ViewVersionLobbyRefsResponse> viewVersionImageRefs(final ViewVersionLobbyRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionLobbyRefs);
    }

    @Override
    public Uni<SyncVersionLobbyRefResponse> syncVersionImageRef(final SyncVersionLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionLobbyRef);
    }

    @Override
    public Uni<DeleteVersionLobbyRefResponse> deleteVersionImageRef(final DeleteVersionLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionLobbyRef);
    }

    @Override
    public Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(GetVersionMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionMatchmakerRef);
    }

    @Override
    public Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(
            final GetVersionMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionMatchmakerRequest);
    }

    @Override
    public Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(
            final FindVersionMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionMatchmakerRequest);
    }

    @Override
    public Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            final ViewVersionMatchmakerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionMatchmakerRequests);
    }

    @Override
    public Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(
            final SyncVersionMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionMatchmakerRequest);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            final DeleteVersionMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionMatchmakerRequest);
    }

    @Override
    public Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(
            final FindVersionMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionMatchmakerRef);
    }

    @Override
    public Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(
            final ViewVersionMatchmakerRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionMatchmakerRefs);
    }

    @Override
    public Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(
            final SyncVersionMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionMatchmakerRef);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(
            final DeleteVersionMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionMatchmakerRef);
    }
}
