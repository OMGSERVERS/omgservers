package com.omgservers.job.match;

import com.omgservers.dto.matchmaker.DeleteMatchCommandRequest;
import com.omgservers.dto.matchmaker.ViewMatchCommandsRequest;
import com.omgservers.dto.matchmaker.ViewMatchCommandsResponse;
import com.omgservers.exception.ServerSideClientExceptionException;
import com.omgservers.job.match.operations.handleMatchCommand.HandleMatchCommandOperation;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
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
    public JobQualifierEnum getJobType() {
        return JobQualifierEnum.MATCH;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var matchmakerId = shardKey;
        final var matchId = entityId;

        return viewMatchCommands(matchmakerId, matchId)
                .call(this::handleMatchCommands)
                .call(this::deleteMatchCommands)
                .invoke(matchCommands -> log.info("Match commands have been processed, " +
                                "count={}, matchmakerId={}, matchId={}",
                        matchCommands.size(), matchmakerId, matchId))
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
