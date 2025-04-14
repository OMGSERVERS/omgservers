package com.omgservers.service.shard.match.impl.service.webService;

import com.omgservers.schema.shard.match.DeleteMatchRequest;
import com.omgservers.schema.shard.match.DeleteMatchResponse;
import com.omgservers.schema.shard.match.GetMatchRequest;
import com.omgservers.schema.shard.match.GetMatchResponse;
import com.omgservers.schema.shard.match.SyncMatchRequest;
import com.omgservers.schema.shard.match.SyncMatchResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Match
     */

    Uni<GetMatchResponse> execute(GetMatchRequest request);

    Uni<SyncMatchResponse> execute(SyncMatchRequest request);

    Uni<DeleteMatchResponse> execute(DeleteMatchRequest request);

}
