package com.omgservers.dto.matchmaker;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DoMatchmakingResponse {

    List<MatchModel> createdMatches;
    List<MatchModel> updatedMatches;
    List<MatchClientModel> matchedClients;
    List<RequestModel> failedRequests;

    public DoMatchmakingResponse() {
        createdMatches = new ArrayList<>();
        updatedMatches = new ArrayList<>();
        matchedClients = new ArrayList<>();
        failedRequests = new ArrayList<>();
    }
}
