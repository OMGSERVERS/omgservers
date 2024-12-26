package com.omgservers.service.module.alias.impl.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.module.alias.impl.operation.alias.SelectAliasesByEntityIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectAliasesByEntityIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectAliasesByEntityIdOperation selectAliasesByEntityIdOperation;

    final PgPool pgPool;

    public List<AliasModel> execute(final Long shardKey, final Long entityId) {
        return pgPool.withTransaction(sqlConnection -> selectAliasesByEntityIdOperation
                        .execute(sqlConnection, 0, shardKey, entityId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
