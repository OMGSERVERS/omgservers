package com.omgservers.operation.executeHasObject;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface ExecuteHasObjectOperation {

    Uni<Boolean> executeHasObject(SqlConnection sqlConnection,
                                  int shard,
                                  String sql,
                                  List<?> parameters,
                                  String objectName);
}
