package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleClosedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleClosedMatchesOperationImpl implements HandleClosedMatchesOperation {

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState) {

        // Detecting closed matches that are empty

        final var matchesToUpdateStatus = matchmakerState.getMatchmakerMatches().stream()
                .filter(matchmakerMatch -> matchmakerMatch.getStatus().equals(MatchmakerMatchStatusEnum.CLOSED))
                .filter(matchmakerMatch -> {
                    final var matchmakerMatchId = matchmakerMatch.getId();
                    return matchmakerState.getMatchmakerMatchAssignments().stream()
                            .noneMatch(matchmakerMatchAssignment -> matchmakerMatchAssignment.getMatchId()
                                    .equals(matchmakerMatchId));
                })
                .peek(matchmakerMatch -> {
                    matchmakerMatch.setStatus(MatchmakerMatchStatusEnum.EMPTY);
                    matchmakerMatch.setModified(Instant.now());
                })
                .toList();

        matchmakerChangeOfState.getMatchesToUpdateStatus().addAll(matchesToUpdateStatus);

        if (!matchesToUpdateStatus.isEmpty()) {
            log.debug("Empty matches were queued to change their status, count={}", matchesToUpdateStatus.size());
        }
    }
}
