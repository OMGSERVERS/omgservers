package com.omgservers.module.matchmaker.impl.operation.deleteMatchmakerCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerCommandOperation {
    Uni<Boolean> deleteMatchmakerCommand(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long matchmakerId,
                                         Long id);
}
