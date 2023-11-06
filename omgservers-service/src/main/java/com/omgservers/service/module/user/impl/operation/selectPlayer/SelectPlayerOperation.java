package com.omgservers.service.module.user.impl.operation.selectPlayer;

import com.omgservers.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectPlayerOperation {
    Uni<PlayerModel> selectPlayer(SqlConnection sqlConnection,
                                  int shard,
                                  Long userId,
                                  Long id,
                                  Boolean deleted);
}
