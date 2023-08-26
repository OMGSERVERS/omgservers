package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService;

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

public interface MatchmakerWebService {

    Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerShardRequest request);

    Uni<GetMatchmakerInternalResponse> getMatchmaker(GetMatchmakerShardRequest request);

    Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerShardRequest request);

    Uni<SyncRequestInternalResponse> syncRequest(SyncRequestShardRequest request);

    Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestShardRequest request);

    Uni<GetMatchInternalResponse> getMatch(GetMatchShardRequest request);

    Uni<SyncMatchInternalResponse> syncMatch(SyncMatchShardRequest request);

    Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request);

    Uni<DoMatchmakingInternalResponse> doMatchmaking(DoMatchmakingShardRequest request);
}
