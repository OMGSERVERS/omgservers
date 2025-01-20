package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation {
    Uni<List<MatchmakerAssignmentModel>> execute(SqlConnection sqlConnection,
                                                 int shard,
                                                 Long matchmakerId);
}
