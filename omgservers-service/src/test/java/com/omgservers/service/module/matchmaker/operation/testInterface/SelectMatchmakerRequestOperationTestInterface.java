package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerRequest.SelectMatchmakerRequestOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectMatchmakerRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectMatchmakerRequestOperation selectMatchmakerRequestOperation;

    final PgPool pgPool;

    public MatchmakerRequestModel selectMatchmakerRequest(final int shard,
                                                          final Long matchmakerId,
                                                          final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerRequestOperation
                        .selectMatchmakerRequest(sqlConnection, shard, matchmakerId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
