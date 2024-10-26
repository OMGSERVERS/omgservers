package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.CloseMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CloseMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.CLOSE_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateDto matchmakerState,
                       final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (CloseMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var matchmakerMatchId = body.getMatchmakerMatchId();

        // Find the match to update its status

        final var matchesToUpdateStatus = matchmakerState.getMatchmakerMatches()
                .stream().filter(matchmakerMatch -> matchmakerMatch.getId().equals(matchmakerMatchId))
                .peek(matchmakerMatch -> matchmakerMatch.setStatus(MatchmakerMatchStatusEnum.CLOSED))
                .toList();

        matchmakerChangeOfState.getMatchesToUpdateStatus().addAll(matchesToUpdateStatus);

        if (!matchesToUpdateStatus.isEmpty()) {
            log.info("The match was queued for closure, matchId={}", matchmakerMatchId);
        }
    }
}
