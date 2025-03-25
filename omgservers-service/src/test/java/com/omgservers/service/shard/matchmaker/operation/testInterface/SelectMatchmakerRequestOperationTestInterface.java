package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.SelectMatchmakerRequestOperation;
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

    public MatchmakerRequestModel selectMatchmakerRequest(final Long matchmakerId,
                                                          final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerRequestOperation
                        .execute(sqlConnection, 0, matchmakerId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
