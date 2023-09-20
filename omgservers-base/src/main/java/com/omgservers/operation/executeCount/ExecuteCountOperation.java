package com.omgservers.operation.executeCount;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface ExecuteCountOperation {

    Uni<Integer> executeCount(SqlConnection sqlConnection,
                              int shard,
                              String sql,
                              List<?> parameters,
                              String objectName);
}
