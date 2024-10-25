package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteMatchMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_MATCH;
    }

    @Override
    public void handle(final MatchmakerStateDto matchmakerState,
                       final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        log.debug("Handle, {}", matchmakerCommand);

        final var body = (DeleteMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var matchmakerMatchId = body.getMatchmakerMatchId();

        // Find the match to delete

        final var matchmakerMatchesToDelete = matchmakerState.getMatchmakerMatches()
                .stream().filter(matchmakerMatch -> matchmakerMatch.getId().equals(matchmakerMatchId))
                .toList();

        matchmakerChangeOfState.getMatchesToDelete().addAll(matchmakerMatchesToDelete);


        log.info("The match was queued for deletion, matchId={}", matchmakerMatchId);
    }
}
