package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.RemoveClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RemoveClientMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.REMOVE_CLIENT;
    }

    @Override
    public boolean handle(final FetchMatchmakerResult fetchMatchmakerResult,
                          final HandleMatchmakerResult handleMatchmakerResult,
                          final MatchmakerCommandModel matchmakerCommand) {
        log.debug("Handle command, {}", matchmakerCommand);

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();
        final var body = (RemoveClientMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var clientId = body.getClientId();

        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        final var matchmakerRequestsToDelete = fetchMatchmakerResult.matchmakerState().getMatchmakerRequests()
                .stream().filter(matchmakerRequest -> matchmakerRequest.getClientId().equals(clientId))
                .map(MatchmakerRequestModel::getId)
                .toList();

        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerRequestsToDelete()
                .addAll(matchmakerRequestsToDelete);

        final var matchmakerMatchAssignmentsToDelete = matchmakerState.getMatchmakerMatchAssignments()
                .stream().filter(matchmakerMatchAssignment -> matchmakerMatchAssignment.getClientId().equals(clientId))
                .map(MatchmakerMatchAssignmentModel::getId)
                .toList();

        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchAssignmentsToDelete()
                .addAll(matchmakerMatchAssignmentsToDelete);

        log.info("Client \"{}\" queued to be removed from matchmaker \"{}\"",
                clientId,
                matchmakerId);

        return true;
    }
}
