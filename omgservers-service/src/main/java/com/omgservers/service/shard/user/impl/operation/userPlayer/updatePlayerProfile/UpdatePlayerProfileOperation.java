package com.omgservers.service.shard.user.impl.operation.userPlayer.updatePlayerProfile;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdatePlayerProfileOperation {
    Uni<Boolean> updatePlayerProfile(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     Long userId,
                                     Long playerId,
                                     Object profile);
}
