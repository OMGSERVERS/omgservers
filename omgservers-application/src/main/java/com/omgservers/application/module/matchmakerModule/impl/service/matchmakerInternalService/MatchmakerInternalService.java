package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService;

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

public interface MatchmakerInternalService {
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
