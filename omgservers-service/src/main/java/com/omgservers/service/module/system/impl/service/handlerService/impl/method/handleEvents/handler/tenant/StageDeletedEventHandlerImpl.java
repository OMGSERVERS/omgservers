package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.tenant;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
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
public class StageDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (StageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getId();

        return tenantModule.getShortcutService().getStage(tenantId, stageId)
                .flatMap(stage -> {
                    log.info("Stage was deleted, {}/{}", tenantId, stageId);

                    return tenantModule.getShortcutService().deleteStagePermissions(tenantId, stageId)
                            .flatMap(voidItem -> tenantModule.getShortcutService()
                                    .deleteVersions(tenantId, stageId))
                            .flatMap(voidItem -> deleteStageJob(tenantId, stageId));
                })
                .replaceWithVoid();
    }

    Uni<Boolean> deleteStageJob(final Long tenantId, final Long stageId) {
        return systemModule.getShortcutService().findStageJob(tenantId, stageId)
                .flatMap(job -> systemModule.getShortcutService().deleteJob(job.getId()));
    }
}
