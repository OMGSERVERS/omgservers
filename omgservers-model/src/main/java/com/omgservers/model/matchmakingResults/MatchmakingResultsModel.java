package com.omgservers.model.matchmakingResults;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MatchmakingResultsModel {

    List<MatchModel> createdMatches;
    List<MatchModel> updatedMatches;
    List<MatchClientModel> matchedClients;
    List<RequestModel> completedRequests;

    public MatchmakingResultsModel() {
        createdMatches = new ArrayList<>();
        updatedMatches = new ArrayList<>();
        matchedClients = new ArrayList<>();
        completedRequests = new ArrayList<>();
    }
}
