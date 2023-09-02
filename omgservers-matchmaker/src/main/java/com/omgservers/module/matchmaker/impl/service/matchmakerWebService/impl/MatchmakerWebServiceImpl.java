package com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl;

import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.MatchmakerShardedService;
import com.omgservers.module.matchmaker.impl.service.matchmakerWebService.MatchmakerWebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerWebServiceImpl implements MatchmakerWebService {

    final MatchmakerShardedService matchmakerShardedService;

    @Override
    public Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request) {
        return matchmakerShardedService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerShardedResponse> getMatchmaker(GetMatchmakerShardedRequest request) {
        return matchmakerShardedService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request) {
        return matchmakerShardedService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestShardedResponse> syncRequest(SyncRequestShardedRequest request) {
        return matchmakerShardedService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request) {
        return matchmakerShardedService.deleteRequest(request);
    }

    @Override
    public Uni<GetMatchShardedResponse> getMatch(GetMatchShardedRequest request) {
        return matchmakerShardedService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchShardedResponse> syncMatch(SyncMatchShardedRequest request) {
        return matchmakerShardedService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request) {
        return matchmakerShardedService.deleteMatch(request);
    }

    @Override
    public Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request) {
        return matchmakerShardedService.syncMatchClient(request);
    }

    @Override
    public Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request) {
        return matchmakerShardedService.deleteMatchClient(request);
    }

    @Override
    public Uni<ExecuteMatchmakerShardedResponse> executeMatchmaker(ExecuteMatchmakerShardedRequest request) {
        return matchmakerShardedService.executeMatchmaker(request);
    }
}
