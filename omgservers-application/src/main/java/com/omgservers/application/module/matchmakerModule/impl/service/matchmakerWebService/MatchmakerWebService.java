package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService;

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

public interface MatchmakerWebService {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request);

    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerRoutedRequest request);

    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerRoutedRequest request);

    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestRoutedRequest request);

    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request);

    Uni<GetMatchInternalResponse> getMatch(GetMatchRoutedRequest request);

    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchRoutedRequest request);

    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchRoutedRequest request);

    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingRoutedRequest request);
}
