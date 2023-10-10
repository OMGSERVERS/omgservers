package com.omgservers.job.matchmaker.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.job.matchmaker.operation.handleMatchmakerCommands.MatchmakerCommandHandler;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.StopMatchMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import com.omgservers.module.matchmaker.MatchmakerModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StopMatchMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    final MatchmakerModule matchmakerModule;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.STOP_MATCH;
    }

    @Override
    public void handle(final IndexedMatchmakerState indexedMatchmakerState,
                       final MatchmakerChangeOfState matchmakerChangeOfState,
                       final MatchmakerCommandModel matchmakerCommand) {
        final var body = (StopMatchMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();

        indexedMatchmakerState.getMatch(matchId)
                .ifPresent(match -> {
                    match.setStopped(true);
                    matchmakerChangeOfState.getUpdatedMatches().add(match);
                });

        matchmakerChangeOfState.getDeletedCommands().add(matchmakerCommand);
    }
}
