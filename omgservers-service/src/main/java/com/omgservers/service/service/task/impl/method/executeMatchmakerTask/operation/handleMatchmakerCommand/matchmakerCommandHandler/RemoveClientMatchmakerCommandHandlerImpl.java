package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.RemoveClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
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
    public void handle(final MatchmakerStateDto matchmakerState,
                       final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        log.trace("Handle command, {}", matchmakerCommand);

        final var body = (RemoveClientMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var clientId = body.getId();

        // Find a client's matchmaker assignments to delete

        final var matchmakerAssignmentsToDelete = matchmakerState.getMatchmakerAssignments()
                .stream().filter(matchmakerAssignment -> matchmakerAssignment.getClientId().equals(clientId))
                .toList();

        matchmakerChangeOfState.getAssignmentsToDelete().addAll(matchmakerAssignmentsToDelete);

        if (!matchmakerAssignmentsToDelete.isEmpty()) {
            log.debug("The client was queued for removal from the matchmaker, clientId={}", clientId);
        }

        // Find a client's matchmaking requests to delete

        final var matchmakerRequestsToDelete = matchmakerState.getMatchmakerRequests()
                .stream().filter(matchmakerRequest -> matchmakerRequest.getClientId().equals(clientId))
                .toList();

        matchmakerChangeOfState.getRequestsToDelete().addAll(matchmakerRequestsToDelete);

        // Find a client's matchmaker match assignments to delete

        final var matchmakerMatchAssignmentsToDelete = matchmakerState.getMatchmakerMatchAssignments()
                .stream().filter(matchmakerMatchAssignment -> matchmakerMatchAssignment.getClientId().equals(clientId))
                .toList();

        matchmakerChangeOfState.getMatchAssignmentsToDelete().addAll(matchmakerMatchAssignmentsToDelete);
    }
}
