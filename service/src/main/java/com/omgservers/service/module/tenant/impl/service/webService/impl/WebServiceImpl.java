package com.omgservers.service.module.tenant.impl.service.webService.impl;

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
import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.service.module.tenant.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ProjectService projectService;
    final VersionService versionService;
    final TenantService tenantService;
    final StageService stageService;

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return tenantService.getTenant(request);
    }

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        return tenantService.getTenantData(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return tenantService.syncTenant(request);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        return tenantService.deleteTenant(request);
    }

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return tenantService.viewTenantPermissions(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(final HasTenantPermissionRequest request) {
        return tenantService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return tenantService.syncTenantPermission(request);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return tenantService.deleteTenantPermission(request);
    }

    @Override
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return projectService.getProject(request);
    }

    @Override
    public Uni<ViewProjectsResponse> viewProjects(final ViewProjectsRequest request) {
        return projectService.viewProjects(request);
    }

    @Override
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        return projectService.syncProject(request);
    }

    @Override
    public Uni<DeleteProjectResponse> deleteProject(final DeleteProjectRequest request) {
        return projectService.deleteProject(request);
    }

    @Override
    public Uni<ViewProjectPermissionsResponse> viewProjectPermissions(final ViewProjectPermissionsRequest request) {
        return projectService.viewProjectPermissions(request);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(final HasProjectPermissionRequest request) {
        return projectService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(final SyncProjectPermissionRequest request) {
        return projectService.syncProjectPermission(request);
    }

    @Override
    public Uni<DeleteProjectPermissionResponse> deleteProjectPermission(final DeleteProjectPermissionRequest request) {
        return projectService.deleteProjectPermission(request);
    }

    @Override
    public Uni<GetStageResponse> getStage(final GetStageRequest request) {
        return stageService.getStage(request);
    }

    @Override
    public Uni<GetStageDataResponse> getStageData(final GetStageDataRequest request) {
        return stageService.getStageData(request);
    }

    @Override
    public Uni<ViewStagesResponse> viewStages(final ViewStagesRequest request) {
        return stageService.viewStages(request);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        return stageService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(final DeleteStageRequest request) {
        return stageService.deleteStage(request);
    }

    @Override
    public Uni<ViewStagePermissionsResponse> viewStagePermissions(final ViewStagePermissionsRequest request) {
        return stageService.viewStagePermissions(request);
    }

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(final HasStagePermissionRequest request) {
        return stageService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(final SyncStagePermissionRequest request) {
        return stageService.syncStagePermission(request);
    }

    @Override
    public Uni<DeleteStagePermissionResponse> deleteStagePermission(final DeleteStagePermissionRequest request) {
        return stageService.deleteStagePermission(request);
    }

    @Override
    public Uni<GetVersionResponse> getVersion(final GetVersionRequest request) {
        return versionService.getVersion(request);
    }

    @Override
    public Uni<GetVersionDataResponse> getVersionData(final GetVersionDataRequest request) {
        return versionService.getVersionData(request);
    }

    @Override
    public Uni<ViewVersionsResponse> viewVersions(final ViewVersionsRequest request) {
        return versionService.viewVersions(request);
    }

    @Override
    public Uni<SelectStageVersionResponse> selectStageVersion(final SelectStageVersionRequest request) {
        return versionService.selectStageVersion(request);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(final SyncVersionRequest request) {
        return versionService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(final DeleteVersionRequest request) {
        return versionService.deleteVersion(request);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(final GetVersionConfigRequest request) {
        return versionService.getVersionConfig(request);
    }

    @Override
    public Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(
            final GetVersionJenkinsRequestRequest request) {
        return versionService.getVersionJenkinsRequest(request);
    }

    @Override
    public Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            final ViewVersionJenkinsRequestsRequest request) {
        return versionService.viewVersionJenkinsRequests(request);
    }

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(
            final SyncVersionJenkinsRequestRequest request) {
        return versionService.syncVersionJenkinsRequest(request);
    }

    @Override
    public Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(
            final DeleteVersionJenkinsRequestRequest request) {
        return versionService.deleteVersionJenkinsRequest(request);
    }

    @Override
    public Uni<GetVersionImageRefResponse> getVersionImageRef(final GetVersionImageRefRequest request) {
        return versionService.getVersionImageRef(request);
    }

    @Override
    public Uni<FindVersionImageRefResponse> findVersionImageRef(final FindVersionImageRefRequest request) {
        return versionService.findVersionImageRef(request);
    }

    @Override
    public Uni<ViewVersionImageRefsResponse> viewVersionImageRefs(final ViewVersionImageRefsRequest request) {
        return versionService.viewVersionImageRefs(request);
    }

    @Override
    public Uni<SyncVersionImageRefResponse> syncVersionImageRef(final SyncVersionImageRefRequest request) {
        return versionService.syncVersionImageRef(request);
    }

    @Override
    public Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(final DeleteVersionImageRefRequest request) {
        return versionService.deleteVersionImageRef(request);
    }

    @Override
    public Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(final GetVersionLobbyRequestRequest request) {
        return versionService.getVersionLobbyRequest(request);
    }

    @Override
    public Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(final FindVersionLobbyRequestRequest request) {
        return versionService.findVersionLobbyRequest(request);
    }

    @Override
    public Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(
            final ViewVersionLobbyRequestsRequest request) {
        return versionService.viewVersionLobbyRequests(request);
    }

    @Override
    public Uni<SyncVersionLobbyRequestResponse> syncVersionLobbyRequest(final SyncVersionLobbyRequestRequest request) {
        return versionService.syncVersionLobbyRequest(request);
    }

    @Override
    public Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(
            final DeleteVersionLobbyRequestRequest request) {
        return versionService.deleteVersionLobbyRequest(request);
    }

    @Override
    public Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(final GetVersionLobbyRefRequest request) {
        return versionService.getVersionLobbyRef(request);
    }

    @Override
    public Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(final FindVersionLobbyRefRequest request) {
        return versionService.findVersionLobbyRef(request);
    }

    @Override
    public Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(final ViewVersionLobbyRefsRequest request) {
        return versionService.viewVersionLobbyRefs(request);
    }

    @Override
    public Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(final SyncVersionLobbyRefRequest request) {
        return versionService.syncVersionLobbyRef(request);
    }

    @Override
    public Uni<DeleteVersionLobbyRefResponse> deleteVersionLobbyRef(final DeleteVersionLobbyRefRequest request) {
        return versionService.deleteVersionLobbyRef(request);
    }

    @Override
    public Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(
            final GetVersionMatchmakerRequestRequest request) {
        return versionService.getVersionMatchmakerRequest(request);
    }

    @Override
    public Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(
            final FindVersionMatchmakerRequestRequest request) {
        return versionService.findVersionMatchmakerRequest(request);
    }

    @Override
    public Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            final ViewVersionMatchmakerRequestsRequest request) {
        return versionService.viewVersionMatchmakerRequests(request);
    }

    @Override
    public Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(
            final SyncVersionMatchmakerRequestRequest request) {
        return versionService.syncVersionMatchmakerRequest(request);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            final DeleteVersionMatchmakerRequestRequest request) {
        return versionService.deleteVersionMatchmakerRequest(request);
    }

    @Override
    public Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(final GetVersionMatchmakerRefRequest request) {
        return versionService.getVersionMatchmakerRef(request);
    }

    @Override
    public Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(
            final SyncVersionMatchmakerRefRequest request) {
        return versionService.syncVersionMatchmakerRef(request);
    }

    @Override
    public Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(
            final FindVersionMatchmakerRefRequest request) {
        return versionService.findVersionMatchmakerRef(request);
    }

    @Override
    public Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(
            final ViewVersionMatchmakerRefsRequest request) {
        return versionService.viewVersionMatchmakerRefs(request);
    }

    @Override
    public Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(
            final DeleteVersionMatchmakerRefRequest request) {
        return versionService.deleteVersionMatchmakerRef(request);
    }
}
