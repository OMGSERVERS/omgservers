package com.omgservers.module.internal.impl.operation.upsertLog;

import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertLogOperation {
    Uni<Boolean> upsertLog(SqlConnection sqlConnection, LogModel logEntry);

    default Boolean upsertLog(long timeout, PgPool pgPool, LogModel logEntry) {
        return pgPool.withTransaction(sqlConnection -> upsertLog(sqlConnection, logEntry))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}