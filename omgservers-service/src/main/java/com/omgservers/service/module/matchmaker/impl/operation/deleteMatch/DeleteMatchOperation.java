package com.omgservers.service.module.matchmaker.impl.operation.deleteMatch;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchOperation {
    Uni<Boolean> deleteMatch(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             Long matchmakerId,
                             Long id);
}
