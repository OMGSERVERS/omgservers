package com.omgservers.service.handler.job.task.matchmaker.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.StopMatchmakingCommandBodyModel;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.handler.job.task.matchmaker.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StopMatchmakingCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.STOP_MATCHMAKING;
    }

    @Override
    public Uni<Void> handle(final MatchmakerState matchmakerState,
                            final MatchmakerChangeOfState changeOfState,
                            final MatchmakerCommandModel matchmakerCommand) {
        final var body = (StopMatchmakingCommandBodyModel) matchmakerCommand.getBody();
        final var matchId = body.getId();

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {

                    final var stoppedMatches = matchmakerState.getMatches()
                            .stream().filter(match -> match.getId().equals(matchId))
                            .peek(match -> match.setStopped(true))
                            .toList();

                    changeOfState.getStoppedMatches().addAll(stoppedMatches);

                    log.info(
                            "Match matchmaking was stopped, " +
                                    "match={}/{}",
                            matchmakerCommand.getMatchmakerId(),
                            matchId);
                });
    }
}
