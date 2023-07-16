package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.*;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.*;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerWebServiceImpl implements MatchmakerWebService {

    final MatchmakerInternalService matchmakerInternalService;

    @Override
    public Uni<Void> createMatchmaker(CreateMatchmakerInternalRequest request) {
        return matchmakerInternalService.createMatchmaker(request);
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
    public Uni<CreateRequestInternalResponse> createRequest(CreateRequestInternalRequest request) {
        return matchmakerInternalService.createRequest(request);
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
