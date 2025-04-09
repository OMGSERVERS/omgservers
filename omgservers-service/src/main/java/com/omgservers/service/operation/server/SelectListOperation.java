package com.omgservers.service.operation.server;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Function;

public interface SelectListOperation {

    <T> Uni<List<T>> selectList(SqlConnection sqlConnection,
                                int shard,
                                String sql,
                                List<?> parameters,
                                String objectName,
                                Function<Row, T> objectMapper);

    <T> Uni<List<T>> selectList(SqlConnection sqlConnection,
                                String sql,
                                List<?> parameters,
                                String objectName,
                                Function<Row, T> objectMapper);
}
