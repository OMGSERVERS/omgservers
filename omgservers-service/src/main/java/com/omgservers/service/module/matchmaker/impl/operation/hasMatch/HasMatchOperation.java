package com.omgservers.service.module.matchmaker.impl.operation.hasMatch;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasMatchOperation {
    Uni<Boolean> hasMatch(SqlConnection sqlConnection,
                          int shard,
                          Long matchmakerId,
                          Long id);
}
