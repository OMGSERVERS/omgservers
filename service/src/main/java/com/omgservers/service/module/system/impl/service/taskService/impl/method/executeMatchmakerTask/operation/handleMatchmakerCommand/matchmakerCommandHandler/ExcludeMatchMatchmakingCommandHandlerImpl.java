package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ExcludeMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.EXCLUDE_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateModel currentState,
                       final MatchmakerChangeOfStateModel changeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (ExcludeMatchMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();

        final var excludedMatched = currentState.getMatches()
                .stream().filter(match -> match.getId().equals(matchId))
                .peek(match -> match.setStatus(MatchmakerMatchStatusEnum.EXCLUDED))
                .toList();

        changeOfState.getMatchesToUpdateStatus().addAll(excludedMatched);

        log.info("Matchmaker match was excluded, match={}/{}",
                matchmakerCommand.getMatchmakerId(),
                matchId);
    }
}
