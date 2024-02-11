package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.tenant;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionMatchmakerDeletedEventBodyModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
public class VersionMatchmakerDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionMatchmakerId = body.getId();

        return tenantModule.getShortcutService().getVersionMatchmaker(tenantId, versionMatchmakerId)
                .flatMap(versionMatchmaker -> {
                    final var matchmakerId = versionMatchmaker.getMatchmakerId();

                    log.info("Version matchmaker was deleted, " +
                                    "versionMatchmaker={}/{}, " +
                                    "versionId={}, " +
                                    "matchmakerId={}",
                            tenantId,
                            versionMatchmakerId,
                            versionMatchmaker.getVersionId(),
                            matchmakerId);

                    return matchmakerModule.getShortcutService().deleteMatchmaker(matchmakerId);
                })
                .replaceWithVoid();
    }
}
