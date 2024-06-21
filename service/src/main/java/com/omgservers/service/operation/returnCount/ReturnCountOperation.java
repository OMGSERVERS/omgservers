package com.omgservers.service.operation.returnCount;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface ReturnCountOperation {

    Uni<Integer> returnCount(SqlConnection sqlConnection,
                             int shard,
                             String sql,
                             List<?> parameters);
}
