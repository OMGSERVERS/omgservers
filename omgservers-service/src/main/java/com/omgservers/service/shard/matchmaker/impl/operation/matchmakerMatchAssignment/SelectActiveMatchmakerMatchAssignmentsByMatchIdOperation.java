package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchAssignmentsByMatchIdOperation {
    Uni<List<MatchmakerMatchAssignmentModel>> execute(SqlConnection sqlConnection,
                                                      int slot,
                                                      Long matchmakerId,
                                                      Long matchId);
}
