package com.omgservers.service.module.user.impl.operation.userPlayer.deletePlayer;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePlayerOperation {
    Uni<Boolean> deletePlayer(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              final Long userId,
                              Long id);
}
