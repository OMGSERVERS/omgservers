package com.omgservers.service.shard.match.service.testInterface;

import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import com.omgservers.service.shard.match.impl.service.matchService.MatchService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchService matchService;

    /*
    Match
     */

    public GetMatchResponse execute(final GetMatchRequest request) {
        return matchService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchResponse execute(final SyncMatchRequest request) {
        return matchService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncMatchResponse executeWithIdempotency(final SyncMatchRequest request) {
        return matchService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteMatchResponse execute(final DeleteMatchRequest request) {
        return matchService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
