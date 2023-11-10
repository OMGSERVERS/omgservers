package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ProjectDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getDeletedProject(tenantId, id)
                .flatMap(project -> {
                    log.info("Project was deleted, project={}/{}", tenantId, id);

                    return viewStages(tenantId, id)
                            .flatMap(stages -> Multi.createFrom().iterable(stages)
                                    .onItem().transformToUniAndConcatenate(stage ->
                                            deleteStage(tenantId, stage.getId()))
                                    .collect().asList()
                                    .replaceWithVoid()
                            );
                })
                .replaceWith(true);
    }

    Uni<ProjectModel> getDeletedProject(final Long tenantId, final Long id) {
        final var request = new GetProjectRequest(tenantId, id);
        return tenantModule.getProjectService().getProject(request)
                .map(GetProjectResponse::getProject);
    }

    Uni<List<StageModel>> viewStages(final Long tenantId, final Long projectId) {
        final var request = new ViewStagesRequest(tenantId, projectId, false);
        return tenantModule.getStageService().viewStages(request)
                .map(ViewStagesResponse::getStages);
    }

    Uni<Boolean> deleteStage(final Long tenantId, final Long id) {
        final var request = new DeleteStageRequest(tenantId, id);
        return tenantModule.getStageService().deleteStage(request)
                .map(DeleteStageResponse::getDeleted);
    }
}
