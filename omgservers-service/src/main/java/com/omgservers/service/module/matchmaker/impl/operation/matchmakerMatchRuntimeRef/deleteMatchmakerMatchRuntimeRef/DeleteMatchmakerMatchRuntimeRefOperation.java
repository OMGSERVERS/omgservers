package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.deleteMatchmakerMatchRuntimeRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerMatchRuntimeRefOperation {
    Uni<Boolean> deleteMatchmakerMatchRuntimeRef(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 Long matchmakerId,
                                                 Long matchId,
                                                 Long id);
}
