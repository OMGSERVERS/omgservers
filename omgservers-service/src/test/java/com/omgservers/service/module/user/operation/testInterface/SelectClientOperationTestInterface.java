package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.client.ClientModel;
import com.omgservers.service.module.user.impl.operation.selectClient.SelectClientOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectClientOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectClientOperation selectClientOperation;

    final PgPool pgPool;

    public ClientModel selectClient(final int shard,
                                    final Long userId,
                                    final Long id,
                                    final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectClientOperation
                        .selectClient(sqlConnection, shard, userId, id, deleted))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
