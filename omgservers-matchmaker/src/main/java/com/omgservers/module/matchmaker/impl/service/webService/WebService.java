package com.omgservers.module.matchmaker.impl.service.webService;

import com.omgservers.dto.matchmaker.DeleteMatchClientRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientResponse;
import com.omgservers.dto.matchmaker.DeleteMatchRequest;
import com.omgservers.dto.matchmaker.DeleteMatchResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.dto.matchmaker.DeleteRequestRequest;
import com.omgservers.dto.matchmaker.DeleteRequestResponse;
import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientResponse;
import com.omgservers.dto.matchmaker.SyncMatchRequest;
import com.omgservers.dto.matchmaker.SyncMatchResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsResponse;
import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.dto.matchmaker.ViewRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<SyncMatchmakerResponse> syncMatchmaker(SyncMatchmakerRequest request);

    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);

    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    Uni<SyncRequestResponse> syncRequest(SyncRequestRequest request);

    Uni<DeleteRequestResponse> deleteRequest(DeleteRequestRequest request);

    Uni<ViewRequestsResponse> viewRequests(ViewRequestsRequest request);

    Uni<GetMatchResponse> getMatch(GetMatchRequest request);

    Uni<SyncMatchResponse> syncMatch(SyncMatchRequest request);

    Uni<DeleteMatchResponse> deleteMatch(DeleteMatchRequest request);

    Uni<ViewMatchesResponse> viewMatches(ViewMatchesRequest request);

    Uni<GetMatchClientResponse> getMatchClient(GetMatchClientRequest request);

    Uni<SyncMatchClientResponse> syncMatchClient(SyncMatchClientRequest request);

    Uni<DeleteMatchClientResponse> deleteMatchClient(DeleteMatchClientRequest request);

    Uni<SyncMatchmakingResultsResponse> syncMatchmakingResults(SyncMatchmakingResultsRequest request);
}
