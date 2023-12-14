package com.omgservers.service.module.tenant.impl.service.shortcutService;

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
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
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
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SelectStageVersionRequest;
import com.omgservers.model.dto.tenant.SelectStageVersionResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
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
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantDashboard.TenantDashboardModel;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final TenantModule tenantModule;

    @Override
    public Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getTenantService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    @Override
    public Uni<TenantDashboardModel> getTenantDashboard(Long id) {
        final var request = new GetTenantDashboardRequest(id);
        return tenantModule.getTenantService().getTenantDashboard(request)
                .map(GetTenantDashboardResponse::getTenantDashboard);
    }

    @Override
    public Uni<Boolean> syncTenantPermission(TenantPermissionModel tenantPermission) {
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionRequest(tenantPermission);
        return tenantModule.getTenantService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .map(SyncTenantPermissionResponse::getCreated);
    }

    @Override
    public Uni<List<TenantPermissionModel>> viewTenantPermissions(final Long tenantId) {
        final var request = new ViewTenantPermissionsRequest(tenantId);
        return tenantModule.getTenantService().viewTenantPermissions(request)
                .map(ViewTenantPermissionsResponse::getTenantPermissions);
    }

    @Override
    public Uni<Boolean> deleteTenantPermission(final Long tenantId, final Long id) {
        final var request = new DeleteTenantPermissionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantPermission(request)
                .map(DeleteTenantPermissionResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteTenantPermissions(final Long tenantId) {
        return viewTenantPermissions(tenantId)
                .flatMap(tenantPermissions -> Multi.createFrom().iterable(tenantPermissions)
                        .onItem().transformToUniAndConcatenate(tenantPermission ->
                                deleteTenantPermission(tenantId, tenantPermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete tenant permission failed, " +
                                                            "tenantId={}, " +
                                                            "tenantPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<ProjectModel> getProject(final Long tenantId, final Long id) {
        final var request = new GetProjectRequest(tenantId, id);
        return tenantModule.getProjectService().getProject(request)
                .map(GetProjectResponse::getProject);
    }

    @Override
    public Uni<List<ProjectModel>> viewProjects(final Long tenantId) {
        final var request = new ViewProjectsRequest(tenantId);
        return tenantModule.getProjectService().viewProjects(request)
                .map(ViewProjectsResponse::getProjects);
    }

    @Override
    public Uni<Boolean> deleteProject(final Long tenantId, final Long id) {
        final var request = new DeleteProjectRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProject(request)
                .map(DeleteProjectResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteProjects(final Long tenantId) {
        return viewProjects(tenantId)
                .flatMap(projects -> Multi.createFrom().iterable(projects)
                        .onItem().transformToUniAndConcatenate(project ->
                                deleteProject(tenantId, project.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete project failed, " +
                                                            "tenantId={}, " +
                                                            "projectId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    project.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<List<ProjectPermissionModel>> viewProjectPermissions(final Long tenantId, final Long projectId) {
        final var request = new ViewProjectPermissionsRequest(tenantId, projectId);
        return tenantModule.getProjectService().viewProjectPermissions(request)
                .map(ViewProjectPermissionsResponse::getProjectPermissions);
    }

    @Override
    public Uni<Boolean> deleteProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteProjectPermissionRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProjectPermission(request)
                .map(DeleteProjectPermissionResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteProjectPermissions(final Long tenantId, final Long projectId) {
        return viewProjectPermissions(tenantId, projectId)
                .flatMap(projectPermissions -> Multi.createFrom().iterable(projectPermissions)
                        .onItem().transformToUniAndConcatenate(projectPermission ->
                                deleteProjectPermission(tenantId, projectPermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete project permission failed, " +
                                                            "project={}/{}, " +
                                                            "projectPermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    projectId,
                                                    projectPermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<Void> validateStageSecret(final Long tenantId, final Long stageId, final String secret) {
        final var validateStageSecretHelpRequest = new ValidateStageSecretRequest(tenantId, stageId, secret);
        return tenantModule.getStageService().validateStageSecret(validateStageSecretHelpRequest)
                .replaceWithVoid();
    }

    @Override
    public Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    public Uni<VersionModel> selectLatestStageVersion(final Long tenantId,
                                                      final Long stageId) {
        final var request = new SelectStageVersionRequest(tenantId,
                stageId,
                SelectStageVersionRequest.Strategy.LATEST);
        return tenantModule.getVersionService().selectStageVersion(request)
                .map(SelectStageVersionResponse::getVersion);
    }

    @Override
    public Uni<List<StageModel>> viewStages(final Long tenantId, final Long projectId) {
        final var request = new ViewStagesRequest(tenantId, projectId);
        return tenantModule.getStageService().viewStages(request)
                .map(ViewStagesResponse::getStages);
    }

    @Override
    public Uni<Boolean> deleteStage(final Long tenantId, final Long id) {
        final var request = new DeleteStageRequest(tenantId, id);
        return tenantModule.getStageService().deleteStage(request)
                .map(DeleteStageResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteStages(final Long tenantId, final Long projectId) {
        return viewStages(tenantId, projectId)
                .flatMap(stages -> Multi.createFrom().iterable(stages)
                        .onItem().transformToUniAndConcatenate(stage ->
                                deleteStage(tenantId, stage.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete stage failed, " +
                                                            "project={}/{}, " +
                                                            "stageId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    projectId,
                                                    stage.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<List<StagePermissionModel>> viewStagePermissions(final Long tenantId, final Long stageId) {
        final var request = new ViewStagePermissionsRequest(tenantId, stageId);
        return tenantModule.getStageService().viewStagePermissions(request)
                .map(ViewStagePermissionsResponse::getStagePermissions);
    }

    @Override
    public Uni<Boolean> deleteStagePermission(final Long tenantId, final Long id) {
        final var request = new DeleteStagePermissionRequest(tenantId, id);
        return tenantModule.getStageService().deleteStagePermission(request)
                .map(DeleteStagePermissionResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteStagePermissions(final Long tenantId, final Long stageId) {
        return viewStagePermissions(tenantId, stageId)
                .flatMap(stagePermissions -> Multi.createFrom().iterable(stagePermissions)
                        .onItem().transformToUniAndConcatenate(stagePermission ->
                                deleteStagePermission(tenantId, stagePermission.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete stage permission failed, " +
                                                            "stage={}/{}, " +
                                                            "stagePermissionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    stageId,
                                                    stagePermission.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    @Override
    public Uni<List<VersionModel>> viewVersions(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersions);
    }

    @Override
    public Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteVersions(final Long tenantId, final Long stageId) {
        return viewVersions(tenantId, stageId)
                .flatMap(versions -> Multi.createFrom().iterable(versions)
                        .onItem().transformToUniAndConcatenate(version ->
                                deleteVersion(tenantId, version.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete version failed, " +
                                                            "stage={}/{}, " +
                                                            "versionId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    stageId,
                                                    version.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<VersionMatchmakerModel> selectVersionMatchmaker(final Long tenantId, final Long versionId) {
        final var request = new SelectVersionMatchmakerRequest(tenantId,
                versionId,
                SelectVersionMatchmakerRequest.Strategy.RANDOM);
        return tenantModule.getVersionService().selectVersionMatchmaker(request)
                .map(SelectVersionMatchmakerResponse::getVersionMatchmaker);
    }

    @Override
    public Uni<VersionRuntimeModel> selectVersionRuntime(final Long tenantId, final Long versionId) {
        final var request = new SelectVersionRuntimeRequest(tenantId,
                versionId,
                SelectVersionRuntimeRequest.Strategy.RANDOM);
        return tenantModule.getVersionService().selectVersionRuntime(request)
                .map(SelectVersionRuntimeResponse::getVersionRuntime);
    }

    @Override
    public Uni<VersionMatchmakerModel> getVersionMatchmaker(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmaker(request)
                .map(GetVersionMatchmakerResponse::getVersionMatchmaker);
    }

    @Override
    public Uni<List<VersionMatchmakerModel>> viewVersionMatchmakers(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakersRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakers(request)
                .map(ViewVersionMatchmakersResponse::getVersionMatchmakers);
    }

    @Override
    public Uni<Boolean> deleteVersionMatchmaker(final Long tenantId, final Long id) {
        final var request = new DeleteVersionMatchmakerRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionMatchmaker(request)
                .map(DeleteVersionMatchmakerResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> deleteVersionMatchmakers(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakers(tenantId, versionId)
                .flatMap(versionMatchmakers -> Multi.createFrom().iterable(versionMatchmakers)
                        .onItem().transformToUniAndConcatenate(versionMatchmaker ->
                                deleteVersionMatchmaker(tenantId, versionMatchmaker.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete version matchmaker failed, " +
                                                            "version={}/{}, " +
                                                            "versionMatchmakerId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    versionId,
                                                    versionMatchmaker.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWith(Boolean.TRUE));
    }

    @Override
    public Uni<VersionConfigModel> getVersionConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionConfig(request)
                .map(GetVersionConfigResponse::getVersionConfig);
    }

    @Override
    public Uni<VersionRuntimeModel> getVersionRuntime(final Long tenantId, final Long id) {
        final var request = new GetVersionRuntimeRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionRuntime(request)
                .map(GetVersionRuntimeResponse::getVersionRuntime);
    }

    @Override
    public Uni<List<VersionRuntimeModel>> viewVersionRuntimes(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionRuntimesRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionRuntimes(request)
                .map(ViewVersionRuntimesResponse::getVersionRuntimes);
    }

    @Override
    public Uni<Boolean> deleteVersionRuntime(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRuntimeRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionRuntime(request)
                .map(DeleteVersionRuntimeResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> deleteVersionRuntimes(final Long tenantId, final Long versionId) {
        return viewVersionRuntimes(tenantId, versionId)
                .flatMap(versionRuntimes -> Multi.createFrom().iterable(versionRuntimes)
                        .onItem().transformToUniAndConcatenate(versionRuntime ->
                                deleteVersionRuntime(tenantId, versionRuntime.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete version runtime failed, " +
                                                            "version={}/{}, " +
                                                            "versionRuntimeId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    versionId,
                                                    versionRuntime.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWith(Boolean.TRUE));
    }
}
