package com.omgservers.job.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.factory.MatchClientModelFactory;
import com.omgservers.factory.MatchModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoGreedyMatchmakingOperationImpl implements DoGreedyMatchmakingOperation {

    final DoGreedyMatchmakingStateFactory doGreedyMatchmakingStateFactory;
    final MatchClientModelFactory matchClientModelFactory;
    final MatchModelFactory matchModelFactory;

    @Override
    public DoGreedyMatchmakingResult doGreedyMatchmaking(final VersionModeModel modeConfig,
                                                         final List<RequestModel> requests,
                                                         final List<MatchModel> matches,
                                                         final List<MatchClientModel> clients) {

        final var doGreedyMatchmakingState = doGreedyMatchmakingStateFactory.build(modeConfig,
                matches,
                clients);

        requests.forEach(doGreedyMatchmakingState::matchRequest);

        return doGreedyMatchmakingState.getResult();
    }
}
