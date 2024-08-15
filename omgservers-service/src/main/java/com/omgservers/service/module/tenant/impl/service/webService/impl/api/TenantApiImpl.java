package com.omgservers.service.module.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.model.internalRole.InternalRoleEnum;
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
import com.omgservers.schema.module.tenant.stage.GetStageDataRequest;
import com.omgservers.schema.module.tenant.stage.GetStageDataResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.version.GetVersionDataRequest;
import com.omgservers.schema.module.tenant.version.GetVersionDataResponse;
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
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantData);
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
    public Uni<GetStageDataResponse> getStageData(final GetStageDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getStageData);
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
    public Uni<GetVersionDataResponse> getVersionData(final GetVersionDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionData);
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
    public Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(
            final GetVersionJenkinsRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionJenkinsRequest);
    }

    @Override
    public Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            final ViewVersionJenkinsRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionJenkinsRequests);
    }

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(
            final SyncVersionJenkinsRequestRequest request) {
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
    public Uni<SyncVersionLobbyRequestResponse> syncVersionJenkinsRequest(
            final SyncVersionLobbyRequestRequest request) {
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
