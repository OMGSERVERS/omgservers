package com.omgservers.service.module.user.impl.operation.userPlayer.updatePlayerAttributes;

import com.omgservers.schema.model.player.PlayerAttributesDto;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdatePlayerAttributesOperation {
    Uni<Boolean> updatePlayerAttributes(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        Long userId,
                                        Long playerId,
                                        PlayerAttributesDto attributes);
}
