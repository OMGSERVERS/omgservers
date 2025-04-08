package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerMatchResourceToUpdateStatusDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteMatchMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_MATCH;
    }

    @Override
    public boolean handle(final FetchMatchmakerResult fetchMatchmakerResult,
                          final HandleMatchmakerResult handleMatchmakerResult,
                          final MatchmakerCommandModel matchmakerCommand) {
        log.trace("Handle command, {}", matchmakerCommand);

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();
        final var body = (DeleteMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();
        final var reason = body.getReason();

        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        matchmakerState.getMatchmakerMatchResources().stream()
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getMatchId().equals(matchId))
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getStatus()
                        .equals(MatchmakerMatchResourceStatusEnum.CREATED))
                .map(MatchmakerMatchResourceModel::getId)
                .forEach(matchmakerMatchResourceId -> {
                    final var dtoToUpdateStatus = new MatchmakerMatchResourceToUpdateStatusDto(
                            matchmakerMatchResourceId,
                            MatchmakerMatchResourceStatusEnum.CLOSED);

                    handleMatchmakerResult.matchmakerChangeOfState()
                            .getMatchmakerMatchResourcesToUpdateStatus()
                            .add(dtoToUpdateStatus);

                    log.info("Match resource \"{}\" from matchmaker \"{}\" " +
                                    "must be deleted due to \"{}\" and marked as closed, matchId={}",
                            matchmakerMatchResourceId,
                            matchmakerId,
                            reason,
                            matchId);

                    final var matchmakerMatchAssignmentToDelete = fetchMatchmakerResult
                            .matchmakerState().getMatchmakerMatchAssignments().stream()
                            .filter(matchmakerMatchAssignment -> matchmakerMatchAssignment.getMatchId().equals(matchId))
                            .map(MatchmakerMatchAssignmentModel::getId)
                            .toList();

                    handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchAssignmentsToDelete()
                            .addAll(matchmakerMatchAssignmentToDelete);
                });

        return true;
    }
}
