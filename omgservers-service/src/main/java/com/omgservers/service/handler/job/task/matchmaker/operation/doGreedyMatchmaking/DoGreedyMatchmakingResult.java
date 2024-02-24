package com.omgservers.service.handler.job.task.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;

import java.util.List;

public record DoGreedyMatchmakingResult(
        List<RequestModel> matchedRequests,
        List<MatchModel> createdMatches,
        List<MatchClientModel> createdMatchClients) {
}