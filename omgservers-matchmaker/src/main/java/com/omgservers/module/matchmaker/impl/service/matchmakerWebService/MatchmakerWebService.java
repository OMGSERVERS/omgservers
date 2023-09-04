package com.omgservers.module.matchmaker.impl.service.matchmakerWebService;

import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchShardedResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.dto.matchmaker.SyncRequestShardedResponse;
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

    Uni<GetMatchClientShardedResponse> getMatchClient(GetMatchClientShardedRequest request);

    Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request);

    Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request);

    Uni<ExecuteMatchmakerShardedResponse> executeMatchmaker(ExecuteMatchmakerShardedRequest request);
}
