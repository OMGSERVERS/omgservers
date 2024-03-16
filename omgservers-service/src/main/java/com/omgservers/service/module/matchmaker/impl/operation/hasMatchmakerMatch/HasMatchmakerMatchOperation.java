package com.omgservers.service.module.matchmaker.impl.operation.hasMatchmakerMatch;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasMatchmakerMatchOperation {
    Uni<Boolean> hasMatchmakerMatch(SqlConnection sqlConnection,
                                    int shard,
                                    Long matchmakerId,
                                    Long id);
}
