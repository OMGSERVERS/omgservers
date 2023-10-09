package com.omgservers.job.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;

import java.util.List;

public record DoGreedyMatchmakingResult(

        List<MatchModel> createdMatches,
        List<MatchModel> updatedMatches,
        List<RequestModel> completedRequests) {
}
