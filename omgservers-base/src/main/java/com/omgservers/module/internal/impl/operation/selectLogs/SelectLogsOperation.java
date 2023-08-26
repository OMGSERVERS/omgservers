package com.omgservers.module.internal.impl.operation.selectLogs;

import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectLogsOperation {
    Uni<List<LogModel>> selectLogs(SqlConnection sqlConnection);

    default List<LogModel> selectLogs(long timeout, PgPool pgPool) {
        return pgPool.withTransaction(this::selectLogs)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
