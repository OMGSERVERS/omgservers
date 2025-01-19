package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerAssignmentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         Long matchmakerId,
                         Long id);
}
