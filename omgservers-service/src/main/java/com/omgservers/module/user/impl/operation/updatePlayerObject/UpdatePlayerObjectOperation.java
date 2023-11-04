package com.omgservers.module.user.impl.operation.updatePlayerObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdatePlayerObjectOperation {
    Uni<Boolean> updatePlayerObject(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    Long userId,
                                    Long playerId,
                                    Object object);
}
