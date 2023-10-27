package com.omgservers.module.runtime.impl.operation.selectRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectRuntimeGrantOperation {
    Uni<RuntimeGrantModel> selectRuntimeGrant(SqlConnection sqlConnection, int shard, Long runtimeId, Long id);

    default RuntimeGrantModel selectRuntimeGrant(long timeout, PgPool pgPool, int shard, Long runtimeId, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectRuntimeGrant(sqlConnection, shard, runtimeId, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
