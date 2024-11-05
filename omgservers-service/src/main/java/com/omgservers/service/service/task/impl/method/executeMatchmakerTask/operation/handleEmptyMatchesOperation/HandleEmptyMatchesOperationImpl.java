package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEmptyMatchesOperation;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleEmptyMatchesOperationImpl implements HandleEmptyMatchesOperation {

    private static final int DELETION_DELAY = 16;

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState) {

        /*
        Find all closed matches that have been empty for more than DELETION_DELAY seconds.
        We need a delay interval before deletion to allow players to disconnect on their own.
        Otherwise, the client may interpret it as an error and display an 'Oops' screen.
         */

        final var now = Instant.now();

        final var matchesToDelete = matchmakerState.getMatchmakerMatches().stream()
                .filter(matchmakerMatch -> matchmakerMatch.getStatus().equals(MatchmakerMatchStatusEnum.EMPTY))
                .filter(matchmakerMatch -> Duration.between(matchmakerMatch.getModified(), now)
                        .getSeconds() > DELETION_DELAY)
                .toList();

        matchmakerChangeOfState.getMatchesToDelete().addAll(matchesToDelete);

        if (!matchesToDelete.isEmpty()) {
            log.info("Empty matches were queued for deletion, count={}", matchesToDelete.size());
        }
    }
}
