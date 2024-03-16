package com.omgservers.service.handler.job.task.matchmaker.operation.handleEndedMatches;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleEndedMatchesOperationImpl implements HandleEndedMatchesOperation {

    @Override
    public void handleEndedMatches(final MatchmakerState matchmakerState,
                                   final MatchmakerChangeOfState changeOfState) {
        // Step 1. Group match clients by matchId
        final var groupedMatchClients = matchmakerState.getMatchClients().stream()
                .collect(Collectors.groupingBy(MatchmakerMatchClientModel::getMatchId));

        // Step 2. Filter out matches without match clients
        final var endedMatches = matchmakerState.getMatches().stream()
                .filter(match -> !groupedMatchClients.containsKey(match.getId()))
                .toList();

        // Step 4. Removing ended matches from current matchmaking
        matchmakerState.getMatches().removeAll(endedMatches);

        // Step 5. Adding for removing
        changeOfState.getEndedMatches().addAll(endedMatches);
    }
}
