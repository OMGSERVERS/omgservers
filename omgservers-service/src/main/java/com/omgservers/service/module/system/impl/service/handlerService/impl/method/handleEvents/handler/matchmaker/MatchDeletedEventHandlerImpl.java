package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.matchmaker;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    log.info("Match was deleted, " +
                                    "match={}/{}, " +
                                    "mode={}, " +
                                    "runtimeId={}",
                            matchmakerId,
                            matchId,
                            match.getConfig().getModeConfig().getName(),
                            runtimeId);

                    return runtimeModule.getShortcutService().deleteRuntime(runtimeId)
                            .flatMap(deleted -> matchmakerModule.getShortcutService()
                                    .deleteMatchCommands(matchmakerId, matchId))
                            .flatMap(voidItem -> matchmakerModule.getShortcutService()
                                    .deleteMatchClients(matchmakerId, matchId));
                })
                .replaceWithVoid();
    }
}
