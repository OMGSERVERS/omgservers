package com.omgservers.service.shard.alias.operation.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasByValueOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectAliasByValueOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectAliasByValueOperation selectAliasByValueOperation;

    final PgPool pgPool;

    public AliasModel execute(final Long shardKey,
                              final Long uniquenessGroup,
                              final String value) {
        return pgPool.withTransaction(sqlConnection -> selectAliasByValueOperation
                        .execute(sqlConnection, 0, shardKey, uniquenessGroup, value))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
