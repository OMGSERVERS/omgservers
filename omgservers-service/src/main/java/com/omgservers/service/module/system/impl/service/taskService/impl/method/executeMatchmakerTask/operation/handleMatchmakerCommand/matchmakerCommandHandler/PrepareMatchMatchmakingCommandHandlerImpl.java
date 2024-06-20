package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PrepareMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.PREPARE_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateModel currentState,
                       final MatchmakerChangeOfStateModel changeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (PrepareMatchMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();

        final var preparedMatches = currentState.getMatches()
                .stream().filter(match -> match.getId().equals(matchId))
                .peek(match -> match.setStatus(MatchmakerMatchStatusEnum.PREPARED))
                .toList();

        changeOfState.getMatchesToUpdateStatus().addAll(preparedMatches);

        log.info("Matchmaker match was prepared, match={}/{}",
                matchmakerCommand.getMatchmakerId(),
                matchId);
    }
}
