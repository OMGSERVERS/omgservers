package com.omgservers.service.shard.match.impl.service.webService.impl.api;

import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Match Shard API")
@Path("/service/v1/shard/match/request")
public interface MatchApi {

    /*
    Match
     */

    @POST
    @Path("/get-match")
    Uni<GetMatchResponse> execute(GetMatchRequest request);

    @POST
    @Path("/sync-match")
    Uni<SyncMatchResponse> execute(SyncMatchRequest request);

    @POST
    @Path("/delete-match")
    Uni<DeleteMatchResponse> execute(DeleteMatchRequest request);
}
