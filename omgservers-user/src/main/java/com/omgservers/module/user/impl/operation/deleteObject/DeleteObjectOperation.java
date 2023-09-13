package com.omgservers.module.user.impl.operation.deleteObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteObjectOperation {
    Uni<Boolean> deleteObject(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long userId,
                              Long playerId,
                              String name);
}
