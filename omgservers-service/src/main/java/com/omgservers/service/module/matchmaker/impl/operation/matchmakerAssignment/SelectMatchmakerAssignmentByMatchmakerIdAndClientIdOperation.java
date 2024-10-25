package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation {
    Uni<MatchmakerAssignmentModel> execute(SqlConnection sqlConnection,
                                           int shard,
                                           Long matchmakerId,
                                           Long clientId);
}
