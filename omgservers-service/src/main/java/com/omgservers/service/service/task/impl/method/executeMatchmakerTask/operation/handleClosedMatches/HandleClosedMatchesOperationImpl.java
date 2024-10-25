package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleClosedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleClosedMatchesOperationImpl implements HandleClosedMatchesOperation {

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState) {

        // Find all closed and empty matches for deletion.

        final var matchesToDelete = matchmakerState.getMatchmakerMatches().stream()
                .filter(matchmakerMatch -> matchmakerMatch.getStatus().equals(MatchmakerMatchStatusEnum.CLOSED))
                .filter(matchmakerMatch -> {
                    final var matchmakerMatchId = matchmakerMatch.getId();
                    return matchmakerState.getMatchmakerMatchAssignments().stream()
                            .noneMatch(matchmakerMatchAssignment -> matchmakerMatchAssignment.getMatchId()
                                    .equals(matchmakerMatchId));
                })
                .toList();

        matchmakerChangeOfState.getMatchesToDelete().addAll(matchesToDelete);

        if (!matchesToDelete.isEmpty()) {
            log.info("Closed and empty matches were queued for deletion, count={}", matchesToDelete.size());
        }
    }
}
