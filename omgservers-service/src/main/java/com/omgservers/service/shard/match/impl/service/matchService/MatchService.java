package com.omgservers.service.shard.match.impl.service.matchService;

import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
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
