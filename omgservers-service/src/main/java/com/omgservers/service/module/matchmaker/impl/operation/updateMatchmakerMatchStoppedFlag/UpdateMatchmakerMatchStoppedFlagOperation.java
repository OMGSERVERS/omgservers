package com.omgservers.service.module.matchmaker.impl.operation.updateMatchmakerMatchStoppedFlag;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateMatchmakerMatchStoppedFlagOperation {
    Uni<Boolean> updateMatchmakerMatchStoppedFlag(ChangeContext<?> changeContext,
                                                  SqlConnection sqlConnection,
                                                  int shard,
                                                  final Long matchmakerId,
                                                  final Long matchId,
                                                  final Boolean stopped);
}
