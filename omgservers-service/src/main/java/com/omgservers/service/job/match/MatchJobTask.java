package com.omgservers.service.job.match;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.job.match.operations.handleMatchCommand.HandleMatchCommandOperation;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.jobService.impl.JobTask;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchJobTask implements JobTask {

    final MatchmakerModule matchmakerModule;

    final HandleMatchCommandOperation handleMatchCommandOperation;

    @Override
    public JobQualifierEnum getJobQualifier() {
        return JobQualifierEnum.MATCH;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var matchmakerId = shardKey;
        final var matchId = entityId;

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .map(match -> {
                    if (match.getDeleted()) {
                        log.info("Match was deleted, skip job execution, match={}/{}", matchmakerId, matchId);
                        return null;
                    } else {
                        return match;
                    }
                })
                .onItem().ifNotNull().transformToUni(match ->
                        matchmakerModule.getShortcutService().viewMatchCommands(matchmakerId, matchId)
                                .call(this::handleMatchCommands)
                                .call(this::deleteMatchCommands))
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
