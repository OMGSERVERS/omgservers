package com.omgservers.service.server.operation.hasObject;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface HasObjectOperation {

    Uni<Boolean> hasObject(SqlConnection sqlConnection,
                           int shard,
                           String sql,
                           List<?> parameters,
                           String objectName);
}
