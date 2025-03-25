package com.omgservers.service.shard.match.impl.service.webService;

import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Match
     */

    Uni<GetMatchResponse> execute(GetMatchRequest request);

    Uni<SyncMatchResponse> execute(SyncMatchRequest request);

    Uni<DeleteMatchResponse> execute(DeleteMatchRequest request);

}
