package com.omgservers.service.shard.user.impl.operation.userPlayer.selectPlayerProfile;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerProfileOperation {
    Uni<Object> selectPlayerProfile(SqlConnection sqlConnection,
                                    int shard,
                                    Long userId,
                                    Long playerId);
}
