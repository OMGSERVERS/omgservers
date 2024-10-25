package com.omgservers.service.module.matchmaker.impl.operation.matchmaker;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasMatchmakerOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long id);
}