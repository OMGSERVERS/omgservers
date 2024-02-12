package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.match;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.match.operations.handleMatchCommand.HandleMatchCommandOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchJobTaskImpl {

    final MatchmakerModule matchmakerModule;

    final HandleMatchCommandOperation handleMatchCommandOperation;

    public Uni<Boolean> executeTask(final Long matchmakerId, final Long matchId) {
        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    if (match.getDeleted()) {
                        log.info("Match was deleted, cancel job execution, match={}/{}", matchmakerId, matchId);
                        return Uni.createFrom().item(false);
                    } else {
                        return handleMatch(match)
                                .replaceWith(true);
                    }
                });
    }

    Uni<Void> handleMatch(final MatchModel match) {
        final var matchmakerId = match.getMatchmakerId();
        final var matchId = match.getId();
        return matchmakerModule.getShortcutService().viewMatchCommands(matchmakerId, matchId)
                .call(this::handleMatchCommands)
                .call(this::deleteMatchCommands)
                .replaceWithVoid();
    }

    Uni<Void> handleMatchCommands(List<MatchCommandModel> matchCommands) {
        return Multi.createFrom().iterable(matchCommands)
                .onItem().transformToUniAndConcatenate(matchCommand -> handleMatchCommandOperation
                        .handleMatchCommand(matchCommand)
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.warn("Handle match command failed, " +
                                            "matchCommand={}/{}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    matchCommand.getMatchmakerId(), matchCommand.getId(),
                                    matchCommand.getQualifier(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return null;
                        })
                        .replaceWithVoid()
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchCommands(List<MatchCommandModel> matchCommands) {
        return Multi.createFrom().iterable(matchCommands)
                .onItem().transformToUniAndConcatenate(matchCommand -> {
                    final var matchmakerId = matchCommand.getMatchmakerId();
                    final var id = matchCommand.getId();
                    return matchmakerModule.getShortcutService().deleteMatchCommand(matchmakerId, id)
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Delete match command failed, " +
                                                "matchCommand={}/{}, " +
                                                "qualifier={}, " +
                                                "{}:{}",
                                        matchCommand.getMatchmakerId(), matchCommand.getId(),
                                        matchCommand.getQualifier(),
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return null;
                            });
                })
                .collect().asList()
                .replaceWithVoid();
    }
}
