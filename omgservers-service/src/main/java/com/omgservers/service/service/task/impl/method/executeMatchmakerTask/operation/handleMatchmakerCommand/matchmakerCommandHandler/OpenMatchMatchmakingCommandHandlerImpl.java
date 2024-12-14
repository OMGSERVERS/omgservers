package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.OpenMatchMatchmakerCommandBodyDto;
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
class OpenMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.OPEN_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateDto matchmakerState,
                       final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        log.trace("Handle command, {}", matchmakerCommand);

        final var body = (OpenMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var matchmakerMatchId = body.getMatchId();

        // Find the match to update its status

        final var matchesToUpdateStatus = matchmakerState.getMatchmakerMatches()
                .stream().filter(match -> match.getId().equals(matchmakerMatchId))
                .peek(match -> match.setStatus(MatchmakerMatchStatusEnum.OPENED))
                .toList();

        matchmakerChangeOfState.getMatchesToUpdateStatus().addAll(matchesToUpdateStatus);

        if (!matchesToUpdateStatus.isEmpty()) {
            log.debug("The match was queued to be opened, matchId={}", matchmakerMatchId);
        }
    }
}
