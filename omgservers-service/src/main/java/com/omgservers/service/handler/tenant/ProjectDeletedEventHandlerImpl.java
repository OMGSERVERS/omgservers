package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.tenant.ProjectDeletedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.service.handler.EventHandler;
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
public class ProjectDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ProjectDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var projectId = body.getId();

        return getProject(tenantId, projectId)
                .flatMap(project -> {
                    log.info("Project was deleted, project={}/{}", tenantId, projectId);

                    return deleteProjectPermissions(tenantId, projectId)
                            .flatMap(voidItem -> deleteStages(tenantId, projectId));
                })
                .replaceWithVoid();
    }

    Uni<ProjectModel> getProject(final Long tenantId, final Long id) {
        final var request = new GetProjectRequest(tenantId, id);
        return tenantModule.getProjectService().getProject(request)
                .map(GetProjectResponse::getProject);
    }

    Uni<Void> deleteProjectPermissions(final Long tenantId, final Long projectId) {
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

    Uni<List<ProjectPermissionModel>> viewProjectPermissions(final Long tenantId, final Long projectId) {
        final var request = new ViewProjectPermissionsRequest(tenantId, projectId);
        return tenantModule.getProjectService().viewProjectPermissions(request)
                .map(ViewProjectPermissionsResponse::getProjectPermissions);
    }

    Uni<Boolean> deleteProjectPermission(final Long tenantId, final Long id) {
        final var request = new DeleteProjectPermissionRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProjectPermission(request)
                .map(DeleteProjectPermissionResponse::getDeleted);
    }

    Uni<Void> deleteStages(final Long tenantId, final Long projectId) {
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

    Uni<List<StageModel>> viewStages(final Long tenantId, final Long projectId) {
        final var request = new ViewStagesRequest(tenantId, projectId);
        return tenantModule.getStageService().viewStages(request)
                .map(ViewStagesResponse::getStages);
    }

    Uni<Boolean> deleteStage(final Long tenantId, final Long id) {
        final var request = new DeleteStageRequest(tenantId, id);
        return tenantModule.getStageService().deleteStage(request)
                .map(DeleteStageResponse::getDeleted);
    }
}
