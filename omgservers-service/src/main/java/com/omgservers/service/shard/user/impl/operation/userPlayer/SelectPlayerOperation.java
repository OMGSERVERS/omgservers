package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.omgservers.schema.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerOperation {
    Uni<PlayerModel> execute(SqlConnection sqlConnection,
                             int slot,
                             Long userId,
                             Long id);
}
