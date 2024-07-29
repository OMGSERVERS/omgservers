package com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.deleteMatchmakerCommand;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerCommandOperation {
    Uni<Boolean> deleteMatchmakerCommand(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long matchmakerId,
                                         Long id);
}
