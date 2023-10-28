package com.omgservers.module.user.impl.operation.selectPlayerObject;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerObjectOperation {
    Uni<Object> selectPlayerObject(SqlConnection sqlConnection,
                                   int shard,
                                   Long userId,
                                   Long playerId);
}
