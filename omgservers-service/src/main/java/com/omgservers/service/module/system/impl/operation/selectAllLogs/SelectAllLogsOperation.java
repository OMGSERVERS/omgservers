package com.omgservers.service.module.system.impl.operation.selectAllLogs;

import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.List;

public interface SelectAllLogsOperation {
    Uni<List<LogModel>> selectAllLogs(SqlConnection sqlConnection);

    default List<LogModel> selectAllLogs(long timeout, PgPool pgPool) {
        return pgPool.withTransaction(this::selectAllLogs)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
