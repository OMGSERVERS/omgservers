package com.omgservers.service.module.matchmaker.impl.operation.matchmaker.hasMatchmaker;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasMatchmakerOperation {
    Uni<Boolean> hasMatchmaker(SqlConnection sqlConnection,
                               int shard,
                               Long id);
}
