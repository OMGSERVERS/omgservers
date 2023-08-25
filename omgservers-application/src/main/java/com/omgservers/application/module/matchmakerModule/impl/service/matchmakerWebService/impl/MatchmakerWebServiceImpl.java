package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request) {
        return matchmakerInternalService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request) {
        return matchmakerInternalService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        return matchmakerInternalService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request) {
        return matchmakerInternalService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request) {
        return matchmakerInternalService.deleteRequest(request);
    }

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request) {
        return matchmakerInternalService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request) {
        return matchmakerInternalService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request) {
        return matchmakerInternalService.deleteMatch(request);
    }

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request) {
        return matchmakerInternalService.doMatchmaking(request);
    }
}
