package com.omgservers.service.handler.tenant;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
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
public class ProjectDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ProjectDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var projectId = body.getId();

        return tenantModule.getShortcutService().getProject(tenantId, projectId)
                .flatMap(project -> {
                    log.info("Project was deleted, project={}/{}", tenantId, projectId);

                    return tenantModule.getShortcutService().deleteProjectPermissions(tenantId, projectId)
                            .flatMap(voidItem -> tenantModule.getShortcutService()
                                    .deleteStages(tenantId, projectId));
                })
                .replaceWith(true);
    }
}
