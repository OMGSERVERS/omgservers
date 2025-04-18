package com.omgservers.service.operation.server;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface ReturnCountOperation {

    Uni<Integer> returnCount(SqlConnection sqlConnection,
                             int slot,
                             String sql,
                             List<?> parameters);
}
