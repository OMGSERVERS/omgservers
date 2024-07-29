package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.doGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.version.VersionModeModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
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
    public DoGreedyMatchmakingResult doGreedyMatchmaking(final Long matchmakerId,
                                                         final VersionModeModel config,
                                                         final List<MatchmakerRequestModel> requests,
                                                         final List<MatchmakerMatchModel> matches,
                                                         final List<MatchmakerMatchClientModel> clients) {

        final var doGreedyMatchmakingState = doGreedyMatchmakingStateFactory.build(matchmakerId,
                config,
                matches,
                clients);

        requests.forEach(doGreedyMatchmakingState::matchRequest);

        return doGreedyMatchmakingState.getResult();
    }
}
