package com.omgservers.service.handler.tenant;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.StageCreatedEventBodyModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.StagePermissionModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final GetConfigOperation getConfigOperation;

    final StagePermissionModelFactory stagePermissionModelFactory;
    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (StageCreatedEventBodyModel) event.getBody();
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

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<StagePermissionModel> syncBuilderPermission(final Long tenantId,
                                                    final Long stageId,
                                                    final String idempotencyKey) {
        final var builderUserId = getConfigOperation.getServiceConfig().defaults().builderUserId();
        final var permission = StagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = stagePermissionModelFactory.create(tenantId,
                stageId,
                builderUserId,
                permission,
                idempotencyKey + "/" + builderUserId + "/" + permission);
        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermissionWithIdempotency(request)
                .replaceWith(stagePermission);
    }

    Uni<StagePermissionModel> syncServicePermission(final Long tenantId,
                                                    final Long stageId,
                                                    final String idempotencyKey) {
        final var serviceUserId = getConfigOperation.getServiceConfig().defaults().serviceUserId();
        final var permission = StagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = stagePermissionModelFactory.create(tenantId,
                stageId,
                serviceUserId,
                permission,
                idempotencyKey + "/" + serviceUserId + "/" + permission);
        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermissionWithIdempotency(request)
                .replaceWith(stagePermission);
    }
}
