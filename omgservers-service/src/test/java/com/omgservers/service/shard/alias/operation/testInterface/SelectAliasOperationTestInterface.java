package com.omgservers.service.shard.alias.operation.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectAliasOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectAliasOperation selectAliasOperation;

    final PgPool pgPool;

    public AliasModel execute(final int slot,
                              final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectAliasOperation
                        .execute(sqlConnection, slot, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
