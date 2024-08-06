package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.deleteMatchmakerAssignment;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerAssignmentOperation {
    Uni<Boolean> deleteMatchmakerAssignment(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            int shard,
                                            Long matchmakerId,
                                            Long id);
}
