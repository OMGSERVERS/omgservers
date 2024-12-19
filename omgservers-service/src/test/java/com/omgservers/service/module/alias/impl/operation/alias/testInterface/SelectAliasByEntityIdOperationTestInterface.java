package com.omgservers.service.module.alias.impl.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasByEntityIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectAliasByEntityIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectAliasByEntityIdOperation selectAliasByEntityIdOperation;

    final PgPool pgPool;

    public AliasModel execute(final Long shardKey, final Long entityId) {
        return pgPool.withTransaction(sqlConnection -> selectAliasByEntityIdOperation
                        .execute(sqlConnection, 0, shardKey, entityId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
