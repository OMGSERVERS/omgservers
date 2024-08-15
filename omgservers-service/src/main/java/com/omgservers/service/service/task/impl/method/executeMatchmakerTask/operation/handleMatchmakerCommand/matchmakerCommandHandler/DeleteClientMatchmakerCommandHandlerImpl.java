package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public void handle(final MatchmakerStateModel currentState,
                       final MatchmakerChangeOfStateModel changeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        log.debug("Handle matchmaker command, {}", matchmakerCommand);

        final var body = (DeleteClientMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var clientId = body.getId();

        // Step 1. Finding client's requests and adding for removing

        final var orphanedRequests = currentState.getRequests().stream()
                .filter(request -> request.getClientId().equals(clientId))
                .toList();

        changeOfState.getRequestsToDelete().addAll((orphanedRequests));

        // Step 2. Removing client's requests from current matchmaking
        currentState.getRequests().removeAll(orphanedRequests);

        // Step 3. Finding client's match clients and adding for removing

        final var orphanedMatchClients = currentState.getClients().stream()
                .filter(matchClient -> matchClient.getClientId().equals(clientId))
                .toList();

        changeOfState.getClientsToDelete().addAll((orphanedMatchClients));

        log.info(
                "Client was deleted from matchmaker, " +
                        "clientId={}, " +
                        "matchmakerId={}, " +
                        "orphanedRequests={}, " +
                        "orphanedMatchClients={}, " +
                        "matchmakerCommandId={}",
                clientId,
                matchmakerCommand.getMatchmakerId(),
                orphanedRequests.size(),
                orphanedMatchClients.size(),
                matchmakerCommand.getId());
    }
}
