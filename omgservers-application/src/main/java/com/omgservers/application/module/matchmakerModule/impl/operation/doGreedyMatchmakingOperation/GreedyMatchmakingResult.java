package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;

import java.util.List;

public record GreedyMatchmakingResult(
        List<RequestModel> matchedRequests,
        List<RequestModel> failedRequests,
        List<MatchModel> preparedMatches) {
}
