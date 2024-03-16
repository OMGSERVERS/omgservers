package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatchCommand;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerMatchCommandOperation {
    Uni<Boolean> deleteMatchmakerMatchCommand(ChangeContext<?> changeContext,
                                              SqlConnection sqlConnection,
                                              int shard,
                                              Long matchmakerId,
                                              Long id);
}
