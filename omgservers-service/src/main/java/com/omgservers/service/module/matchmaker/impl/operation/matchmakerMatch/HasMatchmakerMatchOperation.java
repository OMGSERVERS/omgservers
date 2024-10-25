package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasMatchmakerMatchOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long matchmakerId,
                         Long id);
}
