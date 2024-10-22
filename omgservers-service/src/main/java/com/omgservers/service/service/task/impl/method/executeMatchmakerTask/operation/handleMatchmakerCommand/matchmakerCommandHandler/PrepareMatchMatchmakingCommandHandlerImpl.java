package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyDto;
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
class PrepareMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.PREPARE_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateDto currentState,
                       final MatchmakerChangeOfStateDto changeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (PrepareMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
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
