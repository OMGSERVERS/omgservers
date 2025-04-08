package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
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
class KickClientMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.KICK_CLIENT;
    }

    @Override
    public boolean handle(final FetchMatchmakerResult fetchMatchmakerResult,
                          final HandleMatchmakerResult handleMatchmakerResult,
                          final MatchmakerCommandModel matchmakerCommand) {
        log.trace("Handle command, {}", matchmakerCommand);

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();
        final var body = (KickClientMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var clientId = body.getClientId();
        final var matchId = body.getMatchId();

        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        final var matchmakerMatchAssignmentsToDelete = matchmakerState.getMatchmakerMatchAssignments().stream()
                .filter(matchmakerMatchAssignment -> matchmakerMatchAssignment.getClientId().equals(clientId))
                .filter(matchmakerMatchAssignment -> matchmakerMatchAssignment.getMatchId().equals(matchId))
                .map(MatchmakerMatchAssignmentModel::getId)
                .toList();

        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchAssignmentsToDelete()
                .addAll(matchmakerMatchAssignmentsToDelete);

        log.info("Client \"{}\" must be kicked and assignments queued for deletion, matchmakerId=\"{}\"",
                clientId,
                matchmakerId);

        return true;
    }
}
