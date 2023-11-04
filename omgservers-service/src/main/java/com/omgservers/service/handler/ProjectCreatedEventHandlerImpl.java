package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectCreatedEventHandlerImpl implements EventHandler {

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        log.info("Project was created, projectId={}, tenantId={}", id, tenantId);

        return Uni.createFrom().item(true);
    }
}
