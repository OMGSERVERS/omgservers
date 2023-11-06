package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmaker.SelectMatchmakerOperation;
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

    public MatchmakerModel selectMatchmaker(final int shard,
                                            final Long id,
                                            final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                        .selectMatchmaker(sqlConnection, shard, id, deleted))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
