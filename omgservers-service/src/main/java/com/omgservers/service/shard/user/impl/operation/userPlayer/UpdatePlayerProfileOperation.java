package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdatePlayerProfileOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long userId,
                         Long playerId,
                         Object profile);
}
