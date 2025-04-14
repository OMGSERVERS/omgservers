package com.omgservers.service.shard.match.impl.service.webService.impl;

import com.omgservers.schema.shard.match.DeleteMatchRequest;
import com.omgservers.schema.shard.match.DeleteMatchResponse;
import com.omgservers.schema.shard.match.GetMatchRequest;
import com.omgservers.schema.shard.match.GetMatchResponse;
import com.omgservers.schema.shard.match.SyncMatchRequest;
import com.omgservers.schema.shard.match.SyncMatchResponse;
import com.omgservers.service.shard.match.impl.service.matchService.MatchService;
import com.omgservers.service.shard.match.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final MatchService matchService;

    /*
    Match
     */

    @Override
    public Uni<GetMatchResponse> execute(final GetMatchRequest request) {
        return matchService.execute(request);
    }

    @Override
    public Uni<SyncMatchResponse> execute(final SyncMatchRequest request) {
        return matchService.execute(request);
    }

    @Override
    public Uni<DeleteMatchResponse> execute(final DeleteMatchRequest request) {
        return matchService.execute(request);
    }

}
