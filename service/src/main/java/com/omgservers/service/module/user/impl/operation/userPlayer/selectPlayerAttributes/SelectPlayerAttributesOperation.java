package com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayerAttributes;

import com.omgservers.schema.model.player.PlayerAttributesModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerAttributesOperation {
    Uni<PlayerAttributesModel> selectPlayerAttributes(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long userId,
                                                      Long playerId);
}
