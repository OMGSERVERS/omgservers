package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantProjectDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.DeleteTenantProjectPermissionsOperation;
import com.omgservers.service.handler.operation.DeleteTenantStagesByTenantProjectIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantVersionsByTenantProjectIdOperation;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantProjectDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final DeleteTenantVersionsByTenantProjectIdOperation deleteTenantVersionsByTenantProjectIdOperation;
    final DeleteTenantStagesByTenantProjectIdOperation deleteTenantStagesByTenantProjectIdOperation;
    final DeleteTenantProjectPermissionsOperation deleteTenantProjectPermissionsOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_PROJECT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantProjectDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantProjectId = body.getId();

        return getTenantProject(tenantId, tenantProjectId)
                .flatMap(tenantProject -> {
                    log.debug("Deleted, {}", tenantProject);

                    return deleteTenantProjectPermissionsOperation.execute(tenantId, tenantProjectId)
                            .flatMap(voidItem -> deleteTenantStagesByTenantProjectIdOperation
                                    .execute(tenantId, tenantProjectId))
                            .flatMap(voidItem -> deleteTenantVersionsByTenantProjectIdOperation
                                    .execute(tenantId, tenantProjectId));
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

}
