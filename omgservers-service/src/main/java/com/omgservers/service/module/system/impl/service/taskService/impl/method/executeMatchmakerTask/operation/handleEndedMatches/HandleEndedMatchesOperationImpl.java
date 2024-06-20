package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleEndedMatches;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleEndedMatchesOperationImpl implements HandleEndedMatchesOperation {

    @Override
    public void handleEndedMatches(final MatchmakerStateModel matchmakerStateModel,
                                   final MatchmakerChangeOfStateModel changeOfState) {
        // Step 1. Group match clients by matchId
        final var groupedMatchClients = matchmakerStateModel.getClients().stream()
                .collect(Collectors.groupingBy(MatchmakerMatchClientModel::getMatchId));

        // Step 2. Filter out matches without match clients
        final var endedMatches = matchmakerStateModel.getMatches().stream()
                .filter(match -> !groupedMatchClients.containsKey(match.getId()))
                .toList();

        // Step 4. Removing ended matches from current matchmaking
//        matchmakerStateModel.getMatches().removeAll(endedMatches);
//
//        // Step 5. Adding for removing
//        changeOfState.getMatchesToDelete().addAll(endedMatches);
    }
}
