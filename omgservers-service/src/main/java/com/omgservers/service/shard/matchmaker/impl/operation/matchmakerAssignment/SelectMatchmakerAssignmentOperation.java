package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerAssignmentOperation {
    Uni<MatchmakerAssignmentModel> execute(SqlConnection sqlConnection,
                                           int shard,
                                           Long matchmakerId,
                                           Long id);
}
