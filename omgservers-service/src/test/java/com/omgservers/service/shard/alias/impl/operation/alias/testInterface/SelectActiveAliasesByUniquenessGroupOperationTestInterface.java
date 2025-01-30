package com.omgservers.service.shard.alias.impl.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectActiveAliasesByUniquenessGroupOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveAliasesByUniquenessGroupOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveAliasesByUniquenessGroupOperation selectActiveAliasesByUniquenessGroupOperation;

    final PgPool pgPool;

    public List<AliasModel> execute(final Long shardKey, final Long uniquenessGroup) {
        return pgPool.withTransaction(sqlConnection -> selectActiveAliasesByUniquenessGroupOperation
                        .execute(sqlConnection, 0, shardKey, uniquenessGroup))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
