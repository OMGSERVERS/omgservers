package com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;

import java.util.List;

public record DoGreedyMatchmakingResult(

        List<MatchModel> createdMatches,
        List<MatchModel> updatedMatches,
        List<MatchClientModel> matchedClients,
        List<RequestModel> completedRequests) {
}
