package com.omgservers.testDataFactory;

import com.omgservers.schema.model.match.MatchConfigDto;
import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.service.factory.match.MatchModelFactory;
import com.omgservers.service.shard.match.service.testInterface.MatchServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchTestDataFactory {

    final MatchServiceTestInterface matchService;

    final MatchModelFactory matchModelFactory;

    public MatchModel createMatch(final MatchmakerMatchResourceModel matchmakerMatchResource) {
        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();
        final var matchId = matchmakerMatchResource.getMatchId();

        final var match = matchModelFactory.create(matchId, matchmakerId, MatchConfigDto.create());
        final var request = new SyncMatchRequest(match);
        matchService.execute(request);
        return match;
    }
}
