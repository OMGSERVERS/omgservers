package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.dto.matchmakerModule.DeleteMatchShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingShardRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerShardRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestShardRequest;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerWebServiceImpl implements MatchmakerWebService {

    final MatchmakerInternalService matchmakerInternalService;

    @Override
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request) {
        return matchmakerInternalService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request) {
        return matchmakerInternalService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request) {
        return matchmakerInternalService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request) {
        return matchmakerInternalService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request) {
        return matchmakerInternalService.deleteRequest(request);
    }

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchShardRequest request) {
        return matchmakerInternalService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchShardRequest request) {
        return matchmakerInternalService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request) {
        return matchmakerInternalService.deleteMatch(request);
    }

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request) {
        return matchmakerInternalService.doMatchmaking(request);
    }
}
