package com.omgservers.service.operation.server;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Function;

public interface SelectObjectOperation {

    <T> Uni<T> selectObject(SqlConnection sqlConnection,
                            int slot,
                            String sql,
                            List<?> parameters,
                            String objectName,
                            Function<Row, T> objectMapper);

    <T> Uni<T> selectObject(SqlConnection sqlConnection,
                            String sql,
                            List<?> parameters,
                            String objectName,
                            Function<Row, T> objectMapper);
}
