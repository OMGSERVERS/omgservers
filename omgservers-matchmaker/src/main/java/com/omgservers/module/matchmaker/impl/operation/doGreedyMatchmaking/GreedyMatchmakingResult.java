package com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;

import java.util.List;

public record GreedyMatchmakingResult(
        List<RequestModel> matchedRequests,
        List<RequestModel> failedRequests,
        List<MatchModel> preparedMatches) {
}
