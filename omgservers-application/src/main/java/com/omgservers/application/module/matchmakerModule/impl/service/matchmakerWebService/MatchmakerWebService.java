package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.*;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.*;
import io.smallrye.mutiny.Uni;

public interface MatchmakerWebService {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request);

    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerInternalRequest request);

    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request);

    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestInternalRequest request);

    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestInternalRequest request);

    Uni<GetMatchInternalResponse> getMatch(GetMatchInternalRequest request);

    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request);

    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request);

    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingInternalRequest request);
}
