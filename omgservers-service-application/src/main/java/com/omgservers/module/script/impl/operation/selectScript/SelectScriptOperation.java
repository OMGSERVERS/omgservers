package com.omgservers.module.script.impl.operation.selectScript;

import com.omgservers.model.script.ScriptModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectScriptOperation {
    Uni<ScriptModel> selectScript(SqlConnection sqlConnection, int shard, Long id);

    default ScriptModel selectScript(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> selectScript(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
