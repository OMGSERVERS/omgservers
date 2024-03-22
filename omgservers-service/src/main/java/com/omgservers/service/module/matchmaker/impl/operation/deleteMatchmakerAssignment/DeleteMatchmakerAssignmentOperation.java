package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerAssignment;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerAssignmentOperation {
    Uni<Boolean> deleteMatchmakerAssignment(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            int shard,
                                            Long matchmakerId,
                                            Long id);
}
