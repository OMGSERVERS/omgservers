package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService;

import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchShardResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardResponse;
import io.smallrye.mutiny.Uni;

public interface MatchmakerShardedService {
    Uni<SyncMatchmakerShardResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    Uni<GetMatchmakerShardResponse> getMatchmaker(GetMatchmakerShardedRequest request);

    Uni<DeleteMatchmakerShardResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    Uni<SyncRequestShardResponse> syncRequest(SyncRequestShardedRequest request);

    Uni<DeleteRequestShardResponse> deleteRequest(DeleteRequestShardedRequest request);

    Uni<GetMatchShardResponse> getMatch(GetMatchShardedRequest request);

    Uni<SyncMatchShardResponse> syncMatch(SyncMatchShardedRequest request);

    Uni<DeleteMatchShardResponse> deleteMatch(DeleteMatchShardedRequest request);

    Uni<DoMatchmakingShardResponse> doMatchmaking(DoMatchmakingShardedRequest request);
}
