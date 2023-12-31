package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    log.info("Match was deleted, " +
                                    "matchId={}, " +
                                    "mode={}, " +
                                    "matchmakerId={}, " +
                                    "runtimeId={}",
                            matchId,
                            match.getConfig().getModeConfig().getName(),
                            matchmakerId,
                            runtimeId);

                    return runtimeModule.getShortcutService().deleteRuntime(runtimeId)
                            .flatMap(runtimeWasDeleted -> deleteMatchJob(matchmakerId, matchId))
                            .flatMap(wasMatchJobDeleted -> matchmakerModule.getShortcutService()
                                    .deleteMatchCommands(matchmakerId, matchId))
                            .flatMap(voidItem -> matchmakerModule.getShortcutService()
                                    .deleteMatchClients(matchmakerId, matchId));
                })
                .replaceWith(true);
    }

    Uni<Boolean> deleteMatchJob(final Long matchmakerId, final Long matchId) {
        return systemModule.getShortcutService().findMatchJob(matchmakerId, matchId)
                .flatMap(job -> systemModule.getShortcutService().deleteJob(job.getId()));
    }
}
