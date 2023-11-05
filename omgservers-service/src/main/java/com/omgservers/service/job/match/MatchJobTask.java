package com.omgservers.service.job.match;

import com.omgservers.model.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.model.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.service.exception.ServerSideClientExceptionException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.job.match.operations.handleMatchCommand.HandleMatchCommandOperation;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.jobService.impl.JobTask;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

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

        return getMatch(matchmakerId, matchId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .invoke(runtime -> {
                    if (Objects.isNull(runtime)) {
                        log.warn("Match was not found, skip job execution, " +
                                "matchmakerId={}, matchId={}", matchmakerId, matchId);
                    }
                })
                .onItem().ifNotNull().transformToUni(match -> viewMatchCommands(matchmakerId, matchId)
                        .call(this::handleMatchCommands)
                        .call(this::deleteMatchCommands))
                .replaceWithVoid();
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId, false);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
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
                        .onFailure(ServerSideClientExceptionException.class)
                        .recoverWithNull().replaceWithVoid())
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchCommands(List<MatchCommandModel> matchCommands) {
        return Multi.createFrom().iterable(matchCommands)
                .onItem().transformToUniAndConcatenate(matchCommand -> {
                    final var matchmakerId = matchCommand.getMatchmakerId();
                    final var id = matchCommand.getId();
                    final var request = new DeleteMatchCommandRequest(matchmakerId, id);
                    return matchmakerModule.getMatchmakerService().deleteMatchCommand(request);
                })
                .collect().asList()
                .replaceWithVoid();
    }
}
