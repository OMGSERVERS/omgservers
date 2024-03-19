package com.omgservers.service.handler.job.matchmaker.operation.handleMatchmakerCommand.matchmakerCommandHandler;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.service.handler.job.matchmaker.operation.handleMatchmakerCommand.MatchmakerCommandHandler;
import io.smallrye.mutiny.Uni;
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
    public Uni<Void> handle(final MatchmakerStateModel currentState,
                            final MatchmakerChangeOfStateModel changeOfState,
                            final MatchmakerCommandModel matchmakerCommand) {
        final var body = (ExcludeMatchMatchmakerCommandBodyModel) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {

                    final var excludedMatched = currentState.getMatches()
                            .stream().filter(match -> match.getId().equals(matchId))
                            .peek(match -> match.setStatus(MatchmakerMatchStatusEnum.EXCLUDED))
                            .toList();

                    changeOfState.getMatchesToUpdateStatus().addAll(excludedMatched);

                    log.info("Matchmaker match was excluded, match={}/{}",
                            matchmakerCommand.getMatchmakerId(),
                            matchId);
                });
    }
}
