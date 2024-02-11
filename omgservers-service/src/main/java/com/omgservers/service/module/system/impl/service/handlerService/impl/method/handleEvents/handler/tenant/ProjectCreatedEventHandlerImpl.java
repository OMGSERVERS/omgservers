package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.tenant;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
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
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var projectId = body.getId();

        return tenantModule.getShortcutService().getProject(tenantId, projectId)
                .flatMap(project -> {
                    log.info("Project was created, project={}/{}", tenantId, projectId);
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }
}
