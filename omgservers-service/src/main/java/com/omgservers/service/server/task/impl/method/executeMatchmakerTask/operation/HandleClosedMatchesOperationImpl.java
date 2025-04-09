package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleClosedMatchesOperationImpl implements HandleClosedMatchesOperation {

    @Override
    public void execute(final FetchMatchmakerResult fetchMatchmakerResult,
                        final HandleMatchmakerResult handleMatchmakerResult) {

        // Detecting closed matches that are without assignments

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();
        final var matchmakerMatchResourcesToDelete = matchmakerState.getMatchmakerMatchResources().stream()
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getStatus()
                        .equals(MatchmakerMatchResourceStatusEnum.CLOSED))
                .filter(matchmakerMatchResource -> {
                    final var matchId = matchmakerMatchResource.getMatchId();
                    return matchmakerState.getMatchmakerMatchAssignments().stream()
                            .noneMatch(matchmakerMatchAssignment -> matchmakerMatchAssignment.getMatchId()
                                    .equals(matchId));
                })
                .peek(matchmakerMatchResource -> {
                    final var matchmakerMatchResourceId = matchmakerMatchResource.getId();
                    final var matchmakerId = matchmakerMatchResource.getMatchmakerId();
                    final var matchId = matchmakerMatchResource.getMatchId();

                    log.info("Match resource \"{}\" from matchmaker \"{}\" " +
                                    "is closed and queued for deletion, matchId=\"{}\"",
                            matchmakerMatchResourceId,
                            matchmakerId,
                            matchId);
                })
                .map(MatchmakerMatchResourceModel::getId)
                .toList();

        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchResourcesToDelete()
                .addAll(matchmakerMatchResourcesToDelete);
    }
}
