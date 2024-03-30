package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.selectActiveMatchmakerAssignmentsByMatchmakerId;

import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerAssignmentsByMatchmakerIdOperation {
    Uni<List<MatchmakerAssignmentModel>> selectActiveMatchmakerAssignmentsByMatchmakerId(SqlConnection sqlConnection,
                                                                                         int shard,
                                                                                         Long matchmakerId);
}
