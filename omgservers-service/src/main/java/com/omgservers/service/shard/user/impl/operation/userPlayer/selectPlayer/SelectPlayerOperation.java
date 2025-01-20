package com.omgservers.service.shard.user.impl.operation.userPlayer.selectPlayer;

import com.omgservers.schema.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerOperation {
    Uni<PlayerModel> selectPlayer(SqlConnection sqlConnection,
                                  int shard,
                                  Long userId,
                                  Long id);
}
