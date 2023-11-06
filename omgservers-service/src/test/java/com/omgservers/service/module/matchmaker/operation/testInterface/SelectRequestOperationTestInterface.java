package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.model.request.RequestModel;
import com.omgservers.service.module.matchmaker.impl.operation.selectRequest.SelectRequestOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectRequestOperation selectRequestOperation;

    final PgPool pgPool;

    public RequestModel selectRequest(final int shard,
                                         final Long matchmakerId,
                                         final Long id,
                                         final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectRequestOperation
                        .selectRequest(sqlConnection, shard, matchmakerId, id, deleted))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
