package com.omgservers.service.handler.job.task.match;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchCommandResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchmakerMatchCommandsResponse;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
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

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Void> handleMatch(final MatchmakerMatchModel matchmakerMatch) {
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var matchmakerMatchId = matchmakerMatch.getId();
        return viewMatchCommands(matchmakerId, matchmakerMatchId)
                .call(this::handleMatchCommands)
                .call(this::deleteMatchCommands)
                .replaceWithVoid();
    }

    Uni<List<MatchmakerMatchCommandModel>> viewMatchCommands(final Long matchmakerId, final Long matchId) {
        final var request = new ViewMatchmakerMatchCommandsRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().viewMatchmakerMatchCommands(request)
                .map(ViewMatchmakerMatchCommandsResponse::getMatchmakerMatchCommands);
    }

    Uni<Void> handleMatchCommands(List<MatchmakerMatchCommandModel> matchCommands) {
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

    Uni<Void> deleteMatchCommands(List<MatchmakerMatchCommandModel> matchCommands) {
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
        return matchmakerModule.getMatchmakerService().deleteMatchmakerMatchCommand(request)
                .map(DeleteMatchCommandResponse::getDeleted);
    }
}
