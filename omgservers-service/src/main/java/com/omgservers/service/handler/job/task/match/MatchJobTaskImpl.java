package com.omgservers.service.handler.job.task.match;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.handler.job.task.match.operations.handleMatchCommand.HandleMatchCommandOperation;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
        return getMatch(matchmakerId, matchId)
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

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Void> handleMatch(final MatchModel match) {
        final var matchmakerId = match.getMatchmakerId();
        final var matchId = match.getId();
        return viewMatchCommands(matchmakerId, matchId)
                .call(this::handleMatchCommands)
                .call(this::deleteMatchCommands)
                .replaceWithVoid();
    }

    Uni<List<MatchCommandModel>> viewMatchCommands(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchCommandsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchCommands(request)
                .map(ViewMatchCommandsResponse::getMatchCommands);
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
                    return deleteMatchCommand(matchmakerId, id)
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

    Uni<Boolean> deleteMatchCommand(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchCommandRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().deleteMatchCommand(request)
                .map(DeleteMatchCommandResponse::getDeleted);
    }
}
