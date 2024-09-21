package com.omgservers.service.handler.tenant;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageCreatedEventBodyModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
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
public class TenantStageCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final GetConfigOperation getConfigOperation;

    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;
    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_STAGE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantStageCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getId();

        return getStage(tenantId, stageId)
                .flatMap(stage -> {
                    log.info("Stage was created, stage={}/{}", tenantId, stageId);

                    final var idempotencyKey = event.getId().toString();

                    return syncBuilderPermission(tenantId, stageId, idempotencyKey)
                            .flatMap(permission -> syncServicePermission(tenantId, stageId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantStagePermissionModel> syncBuilderPermission(final Long tenantId,
                                                          final Long stageId,
                                                          final String idempotencyKey) {
        final var builderUserId = getConfigOperation.getServiceConfig().defaults().builderUserId();
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = tenantStagePermissionModelFactory.create(tenantId,
                stageId,
                builderUserId,
                permission,
                idempotencyKey + "/" + builderUserId + "/" + permission);
        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getTenantService().syncStagePermissionWithIdempotency(request)
                .replaceWith(stagePermission);
    }

    Uni<TenantStagePermissionModel> syncServicePermission(final Long tenantId,
                                                          final Long stageId,
                                                          final String idempotencyKey) {
        final var serviceUserId = getConfigOperation.getServiceConfig().defaults().serviceUserId();
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = tenantStagePermissionModelFactory.create(tenantId,
                stageId,
                serviceUserId,
                permission,
                idempotencyKey + "/" + serviceUserId + "/" + permission);
        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getTenantService().syncStagePermissionWithIdempotency(request)
                .replaceWith(stagePermission);
    }
}
