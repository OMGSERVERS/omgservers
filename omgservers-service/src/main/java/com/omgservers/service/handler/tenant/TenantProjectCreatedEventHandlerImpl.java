package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantProjectCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_PROJECT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var projectId = body.getId();

        return getProject(tenantId, projectId)
                .flatMap(project -> {
                    log.info("Project was created, project={}/{}", tenantId, projectId);
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getTenantService().getProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }
}
