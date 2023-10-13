package com.omgservers.job.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;

import java.util.List;

public record DoGreedyMatchmakingResult(
        List<MatchModel> endedMatches,
        List<MatchModel> createdMatches,
        List<MatchClientModel> createdMatchClients) {
}
