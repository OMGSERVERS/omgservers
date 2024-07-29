package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.selectMatchmakerAssignment;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerAssignmentOperation {
    Uni<MatchmakerAssignmentModel> selectMatchmakerAssignment(SqlConnection sqlConnection,
                                                              int shard,
                                                              Long matchmakerId,
                                                              Long id);
}
