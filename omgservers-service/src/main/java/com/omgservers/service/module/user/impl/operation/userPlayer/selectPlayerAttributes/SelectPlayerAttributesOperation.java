package com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayerAttributes;

import com.omgservers.schema.model.player.PlayerAttributesDto;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPlayerAttributesOperation {
    Uni<PlayerAttributesDto> selectPlayerAttributes(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long userId,
                                                    Long playerId);
}
