package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteAttributeOperation {
    Uni<Boolean> deleteAttribute(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 int shard,
                                 Long userId,
                                 Long playerId,
                                 String name);
}
