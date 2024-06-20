package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.doGreedyMatchmaking;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;

import java.util.List;

public record DoGreedyMatchmakingResult(
        List<MatchmakerRequestModel> matchedRequests,
        List<MatchmakerMatchModel> createdMatches,
        List<MatchmakerMatchClientModel> createdClients) {
}
