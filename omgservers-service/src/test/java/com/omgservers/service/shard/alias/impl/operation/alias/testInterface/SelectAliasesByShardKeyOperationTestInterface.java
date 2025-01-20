package com.omgservers.service.shard.alias.impl.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.shard.alias.impl.operation.alias.SelectAliasesByShardKeyOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectAliasesByShardKeyOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectAliasesByShardKeyOperation selectAliasesByShardKeyOperation;

    final PgPool pgPool;

    public List<AliasModel> execute(final Long shardKey) {
        return pgPool.withTransaction(sqlConnection -> selectAliasesByShardKeyOperation
                        .execute(sqlConnection, 0, shardKey))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
