package com.omgservers.service.module.user.impl.operation.userPlayer.upsertPlayer;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPlayerOperation {
    Uni<Boolean> upsertPlayer(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              PlayerModel player);
}
