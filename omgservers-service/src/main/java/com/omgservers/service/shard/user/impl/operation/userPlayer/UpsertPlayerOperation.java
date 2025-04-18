package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPlayerOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         PlayerModel player);
}
