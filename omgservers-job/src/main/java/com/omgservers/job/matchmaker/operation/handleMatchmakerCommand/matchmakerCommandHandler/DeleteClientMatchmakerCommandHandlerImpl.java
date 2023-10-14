package com.omgservers.job.matchmaker.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.job.matchmaker.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchCommandModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    final MatchmakerModule matchmakerModule;

    final MatchCommandModelFactory matchCommandModelFactory;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public Uni<Void> handle(final MatchmakerState matchmakerState,
                            final MatchmakerChangeOfState changeOfState,
                            final MatchmakerCommandModel matchmakerCommand) {
        final var body = (DeleteClientMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var clientId = body.getId();

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    // Step 1. Finding client's requests and adding for removing

                    final var orphanedRequests = matchmakerState.getRequests().stream()
                            .filter(request -> request.getClientId().equals(clientId))
                            .toList();

                    changeOfState.getCompletedRequests().addAll((orphanedRequests));

                    // Step 2. Removing client's requests from current matchmaking
                    matchmakerState.getRequests().removeAll(orphanedRequests);

                    // Step 3. Finding client's match clients and adding for removing

                    final var orphanedMatchClients = matchmakerState.getMatchClients().stream()
                            .filter(matchClient -> matchClient.getClientId().equals(clientId))
                            .toList();

                    changeOfState.getOrphanedMatchClients().addAll((orphanedMatchClients));

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
                });
    }
}
