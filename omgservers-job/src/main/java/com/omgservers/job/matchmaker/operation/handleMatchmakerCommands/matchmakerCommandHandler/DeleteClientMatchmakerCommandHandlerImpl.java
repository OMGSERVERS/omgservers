package com.omgservers.job.matchmaker.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.job.matchmaker.operation.handleMatchmakerCommands.MatchmakerCommandHandler;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import com.omgservers.module.matchmaker.MatchmakerModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    final MatchmakerModule matchmakerModule;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public void handle(final IndexedMatchmakerState indexedMatchmakerState,
                       final MatchmakerChangeOfState matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (DeleteClientMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var clientId = body.getId();

        indexedMatchmakerState.getMatchClient(clientId)
                .ifPresent(matchClient -> {
                    matchmakerChangeOfState.getDeletedMatchClients().add(matchClient);

                    final var matchId = matchClient.getMatchId();
                    indexedMatchmakerState.getMatch(matchId)
                            .ifPresent(match -> {
                                final var modifiedMatchGroups = match.getConfig().getGroups().stream()
                                        .filter(group -> group.getRequests()
                                                .removeIf(request -> request.getClientId().equals(clientId)))
                                        .toList();

                                if (modifiedMatchGroups.size() > 0) {
                                    matchmakerChangeOfState.getUpdatedMatches().add(match);
                                }
                            });
                });

        matchmakerChangeOfState.getDeletedMatchmakerCommands().add(matchmakerCommand);
    }
}
