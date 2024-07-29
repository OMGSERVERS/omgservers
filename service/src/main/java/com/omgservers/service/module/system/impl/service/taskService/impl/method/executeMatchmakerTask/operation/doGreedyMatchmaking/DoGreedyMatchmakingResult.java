package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.doGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;

import java.util.List;

public record DoGreedyMatchmakingResult(
        List<MatchmakerRequestModel> matchedRequests,
        List<MatchmakerMatchModel> createdMatches,
        List<MatchmakerMatchClientModel> createdClients) {
}
