package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.SelectMatchmakerOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectMatchmakerOperation selectMatchmakerOperation;

    final PgPool pgPool;

    public MatchmakerModel selectMatchmaker(final int slot,
                                            final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                        .execute(sqlConnection, slot, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
