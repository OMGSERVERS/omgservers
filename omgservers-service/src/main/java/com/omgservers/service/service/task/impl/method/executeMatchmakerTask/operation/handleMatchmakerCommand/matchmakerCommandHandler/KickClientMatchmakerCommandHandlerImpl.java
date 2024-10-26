package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
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
    public void handle(final MatchmakerStateDto matchmakerState,
                       final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        log.debug("Handle, {}", matchmakerCommand);

        final var body = (KickClientMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var clientId = body.getClientId();
        final var matchmakerMatchId = body.getMatchmakerMatchId();

        // Find a client's matchmaker match assignments to delete

        final var matchmakerMatchAssignmentsToDelete = matchmakerState.getMatchmakerMatchAssignments().stream()
                .filter(matchClient -> matchClient.getClientId().equals(clientId))
                .filter(matchClient -> matchClient.getMatchId().equals(matchmakerMatchId))
                .toList();

        matchmakerChangeOfState.getMatchAssignmentsToDelete().addAll(matchmakerMatchAssignmentsToDelete);

        if (!matchmakerMatchAssignmentsToDelete.isEmpty()) {
            log.info("The client was queued to be removed from the match, clientId={}, matchId={}", clientId,
                    matchmakerMatchId);
        }
    }
}
