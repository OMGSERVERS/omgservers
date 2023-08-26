package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.MatchmakerWebService;
import com.omgservers.dto.matchmakerModule.DeleteMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import com.omgservers.dto.matchmakerModule.DoMatchmakingRoutedRequest;
import com.omgservers.dto.matchmakerModule.DoMatchmakingInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.GetMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.dto.matchmakerModule.SyncRequestRoutedRequest;
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
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request) {
        return matchmakerInternalService.syncMatchmaker(request);
    }

    @Override
    public Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerRoutedRequest request) {
        return matchmakerInternalService.getMatchmaker(request);
    }

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerRoutedRequest request) {
        return matchmakerInternalService.deleteMatchmaker(request);
    }

    @Override
    public Uni<SyncRequestInternalResponse> syncRequest(SyncRequestRoutedRequest request) {
        return matchmakerInternalService.syncRequest(request);
    }

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request) {
        return matchmakerInternalService.deleteRequest(request);
    }

    @Override
    public Uni<GetMatchInternalResponse> getMatch(GetMatchRoutedRequest request) {
        return matchmakerInternalService.getMatch(request);
    }

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchRoutedRequest request) {
        return matchmakerInternalService.syncMatch(request);
    }

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchRoutedRequest request) {
        return matchmakerInternalService.deleteMatch(request);
    }

    @Override
    public Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingRoutedRequest request) {
        return matchmakerInternalService.doMatchmaking(request);
    }
}
