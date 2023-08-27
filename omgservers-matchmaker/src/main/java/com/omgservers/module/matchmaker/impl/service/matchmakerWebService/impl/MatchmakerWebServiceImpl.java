package com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl;

import com.omgservers.dto.matchmaker.DeleteMatchShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
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
    public Uni<SyncMatchmakerShardResponse> syncMatchmaker(SyncMatchmakerShardedRequest request) {
        return matchmakerShardedService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerShardResponse> getMatchmaker(GetMatchmakerShardedRequest request) {
        return matchmakerShardedService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerShardResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request) {
        return matchmakerShardedService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestShardResponse> syncRequest(SyncRequestShardedRequest request) {
        return matchmakerShardedService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestShardResponse> deleteRequest(DeleteRequestShardedRequest request) {
        return matchmakerShardedService.deleteRequest(request);
    }

    @Override
    public Uni<GetMatchShardResponse> getMatch(GetMatchShardedRequest request) {
        return matchmakerShardedService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchShardResponse> syncMatch(SyncMatchShardedRequest request) {
        return matchmakerShardedService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchShardResponse> deleteMatch(DeleteMatchShardedRequest request) {
        return matchmakerShardedService.deleteMatch(request);
    }

    @Override
    public Uni<DoMatchmakingShardResponse> doMatchmaking(DoMatchmakingShardedRequest request) {
        return matchmakerShardedService.doMatchmaking(request);
    }
}
