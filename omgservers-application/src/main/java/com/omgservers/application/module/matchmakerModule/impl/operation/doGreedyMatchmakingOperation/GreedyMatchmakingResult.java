package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;

import java.util.List;

public record GreedyMatchmakingResult(
        List<RequestModel> matchedRequests,
        List<RequestModel> failedRequests,
        List<MatchModel> preparedMatches) {
}
