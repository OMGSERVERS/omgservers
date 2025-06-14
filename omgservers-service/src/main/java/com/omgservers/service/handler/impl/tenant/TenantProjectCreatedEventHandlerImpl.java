package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantProjectCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    final GetServiceConfigOperation getServiceConfigOperation;
    final GetIdByUserOperation getIdByUserOperation;

    final TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_PROJECT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantProjectId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantProject(tenantId, tenantProjectId)
                .flatMap(tenantProject -> {
                    log.debug("Created, {}", tenantProject);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }
}
