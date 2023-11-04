package com.omgservers.module.matchmaker.impl.operation.updateMatchStoppedFlag;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateMatchStoppedFlagOperation {
    Uni<Boolean> updateMatch(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             final Long matchmakerId,
                             final Long matchId,
                             final Boolean stopped);
}
