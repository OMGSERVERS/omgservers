package com.omgservers.service.handler.job.task.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.service.factory.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.MatchmakerMatchModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoGreedyMatchmakingOperationImpl implements DoGreedyMatchmakingOperation {

    final DoGreedyMatchmakingStateFactory doGreedyMatchmakingStateFactory;
    final MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;
    final MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Override
    public DoGreedyMatchmakingResult doGreedyMatchmaking(final VersionModeModel modeConfig,
                                                         final List<MatchmakerRequestModel> requests,
                                                         final List<MatchmakerMatchModel> matches,
                                                         final List<MatchmakerMatchClientModel> clients) {

        final var doGreedyMatchmakingState = doGreedyMatchmakingStateFactory.build(modeConfig,
                matches,
                clients);

        requests.forEach(doGreedyMatchmakingState::matchRequest);

        return doGreedyMatchmakingState.getResult();
    }
}
