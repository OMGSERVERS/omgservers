package com.omgservers.operation.executeSelectList;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Function;

public interface ExecuteSelectListOperation {

    <T> Uni<List<T>> executeSelectList(SqlConnection sqlConnection,
                                       int shard,
                                       String sql,
                                       List<?> parameters,
                                       String objectName,
                                       Function<Row, T> objectMapper);
}
