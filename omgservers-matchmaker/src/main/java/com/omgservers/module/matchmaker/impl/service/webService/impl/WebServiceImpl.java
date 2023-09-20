package com.omgservers.module.matchmaker.impl.service.webService.impl;

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
import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.module.matchmaker.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final MatchmakerService matchmakerService;

    @Override
    public Uni<SyncMatchmakerResponse> syncMatchmaker(final SyncMatchmakerRequest request) {
        return matchmakerService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerResponse> getMatchmaker(final GetMatchmakerRequest request) {
        return matchmakerService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerResponse> deleteMatchmaker(final DeleteMatchmakerRequest request) {
        return matchmakerService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestResponse> syncRequest(final SyncRequestRequest request) {
        return matchmakerService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestResponse> deleteRequest(final DeleteRequestRequest request) {
        return matchmakerService.deleteRequest(request);
    }

    @Override
    public Uni<ViewRequestsResponse> viewRequests(final ViewRequestsRequest request) {
        return matchmakerService.viewRequests(request);
    }

    @Override
    public Uni<GetMatchResponse> getMatch(final GetMatchRequest request) {
        return matchmakerService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchResponse> syncMatch(final SyncMatchRequest request) {
        return matchmakerService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchResponse> deleteMatch(final DeleteMatchRequest request) {
        return matchmakerService.deleteMatch(request);
    }

    @Override
    public Uni<ViewMatchesResponse> viewMatches(final ViewMatchesRequest request) {
        return matchmakerService.viewMatches(request);
    }

    @Override
    public Uni<GetMatchClientResponse> getMatchClient(final GetMatchClientRequest request) {
        return matchmakerService.getMatchClient(request);
    }

    @Override
    public Uni<SyncMatchClientResponse> syncMatchClient(final SyncMatchClientRequest request) {
        return matchmakerService.syncMatchClient(request);
    }

    @Override
    public Uni<DeleteMatchClientResponse> deleteMatchClient(final DeleteMatchClientRequest request) {
        return matchmakerService.deleteMatchClient(request);
    }

    @Override
    public Uni<SyncMatchmakingResultsResponse> syncMatchmakingResults(final SyncMatchmakingResultsRequest request) {
        return matchmakerService.syncMatchmakingResults(request);
    }
}
