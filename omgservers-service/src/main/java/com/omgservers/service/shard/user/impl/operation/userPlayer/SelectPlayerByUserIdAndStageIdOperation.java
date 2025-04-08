package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.omgservers.schema.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerByUserIdAndStageIdOperation {
    Uni<PlayerModel> execute(SqlConnection sqlConnection,
                             int shard,
                             Long userId,
                             Long tenantStageId);
}
