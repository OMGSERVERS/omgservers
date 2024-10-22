package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEndedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleEndedMatchesOperationImpl implements HandleEndedMatchesOperation {

    @Override
    public void handleEndedMatches(final MatchmakerStateDto matchmakerStateDto,
                                   final MatchmakerChangeOfStateDto changeOfState) {
        // Step 1. Group match clients by matchId
        final var groupedMatchClients = matchmakerStateDto.getClients().stream()
                .collect(Collectors.groupingBy(MatchmakerMatchClientModel::getMatchId));

        // Step 2. Filter out matches without match clients
        final var endedMatches = matchmakerStateDto.getMatches().stream()
                .filter(match -> !groupedMatchClients.containsKey(match.getId()))
                .toList();

        // Step 4. Removing ended matches from current matchmaking
//        matchmakerStateDto.getMatches().removeAll(endedMatches);
//
//        // Step 5. Adding for removing
//        changeOfState.getMatchesToDelete().addAll(endedMatches);
    }
}
