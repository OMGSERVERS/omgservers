package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantProjectCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
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

    final GetConfigOperation getConfigOperation;

    final TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;
    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_PROJECT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantProject(tenantId, id)
                .flatMap(tenantProject -> {
                    log.debug("Created, {}", tenantProject);

                    return syncBuilderPermission(tenantId, id, idempotencyKey)
                            .flatMap(permission -> syncServicePermission(tenantId, id, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<TenantProjectPermissionModel> syncBuilderPermission(final Long tenantId,
                                                            final Long tenantStageId,
                                                            final String idempotencyKey) {
        final var builderUserId = getConfigOperation.getServiceConfig().defaults().builderUserId();
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGEMENT;
        final var projectPermission = tenantProjectPermissionModelFactory.create(tenantId,
                tenantStageId,
                builderUserId,
                permission,
                idempotencyKey + "/" + builderUserId + "/" + permission);
        final var request = new SyncTenantProjectPermissionRequest(projectPermission);
        return tenantModule.getService().syncTenantProjectPermissionWithIdempotency(request)
                .replaceWith(projectPermission);
    }

    Uni<TenantProjectPermissionModel> syncServicePermission(final Long tenantId,
                                                            final Long tenantStageId,
                                                            final String idempotencyKey) {
        final var serviceUserId = getConfigOperation.getServiceConfig().defaults().serviceUserId();
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGEMENT;
        final var projectPermission = tenantProjectPermissionModelFactory.create(tenantId,
                tenantStageId,
                serviceUserId,
                permission,
                idempotencyKey + "/" + serviceUserId + "/" + permission);
        final var request = new SyncTenantProjectPermissionRequest(projectPermission);
        return tenantModule.getService().syncTenantProjectPermissionWithIdempotency(request)
                .replaceWith(projectPermission);
    }
}
