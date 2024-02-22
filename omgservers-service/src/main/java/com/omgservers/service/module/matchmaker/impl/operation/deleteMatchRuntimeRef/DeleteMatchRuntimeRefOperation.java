package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchRuntimeRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchRuntimeRefOperation {
    Uni<Boolean> deleteMatchRuntimeRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long matchmakerId,
                                       Long matchId,
                                       Long id);
}
