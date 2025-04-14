package com.omgservers.service.shard.match.impl.service.matchService;

import com.omgservers.schema.shard.match.DeleteMatchRequest;
import com.omgservers.schema.shard.match.DeleteMatchResponse;
import com.omgservers.schema.shard.match.GetMatchRequest;
import com.omgservers.schema.shard.match.GetMatchResponse;
import com.omgservers.schema.shard.match.SyncMatchRequest;
import com.omgservers.schema.shard.match.SyncMatchResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface MatchService {

    /*
    Match
     */

    Uni<GetMatchResponse> execute(@Valid GetMatchRequest request);

    Uni<SyncMatchResponse> execute(@Valid SyncMatchRequest request);

    Uni<SyncMatchResponse> executeWithIdempotency(@Valid SyncMatchRequest request);

    Uni<DeleteMatchResponse> execute(@Valid DeleteMatchRequest request);
}
