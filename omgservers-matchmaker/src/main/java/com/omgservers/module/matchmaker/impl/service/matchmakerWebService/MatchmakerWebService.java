package com.omgservers.module.matchmaker.impl.service.matchmakerWebService;

import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedResponse;
import com.omgservers.dto.matchmaker.DoMatchmakingShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import io.smallrye.mutiny.Uni;

public interface MatchmakerWebService {

    Uni<SyncMatchmakerShardedResponse> syncMatchmaker(SyncMatchmakerShardedRequest request);

    Uni<GetMatchmakerShardedResponse> getMatchmaker(GetMatchmakerShardedRequest request);

    Uni<DeleteMatchmakerShardedResponse> deleteMatchmaker(DeleteMatchmakerShardedRequest request);

    Uni<SyncRequestShardedResponse> syncRequest(SyncRequestShardedRequest request);

    Uni<DeleteRequestShardedResponse> deleteRequest(DeleteRequestShardedRequest request);

    Uni<GetMatchShardedResponse> getMatch(GetMatchShardedRequest request);

    Uni<SyncMatchShardedResponse> syncMatch(SyncMatchShardedRequest request);

    Uni<DeleteMatchShardedResponse> deleteMatch(DeleteMatchShardedRequest request);

    Uni<DoMatchmakingShardedResponse> doMatchmaking(DoMatchmakingShardedRequest request);
}