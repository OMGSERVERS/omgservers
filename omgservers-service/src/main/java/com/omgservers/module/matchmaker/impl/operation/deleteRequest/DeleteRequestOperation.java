package com.omgservers.module.matchmaker.impl.operation.deleteRequest;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRequestOperation {
    Uni<Boolean> deleteRequest(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long matchmakerId,
                               Long id);
}
