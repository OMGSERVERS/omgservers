package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var projectId = body.getId();

        return getProject(tenantId, projectId)
                .flatMap(project -> {
                    log.info("Project was created, {}/{}", tenantId, projectId);
                    return Uni.createFrom().voidItem();
                })
                .replaceWith(true);
    }

    Uni<ProjectModel> getProject(final Long tenantId, final Long id) {
        final var request = new GetProjectRequest(tenantId, id, false);
        return tenantModule.getProjectService().getProject(request)
                .map(GetProjectResponse::getProject);
    }
}
