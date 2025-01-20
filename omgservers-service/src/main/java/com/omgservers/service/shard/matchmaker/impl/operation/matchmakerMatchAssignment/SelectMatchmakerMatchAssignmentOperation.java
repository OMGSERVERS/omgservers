package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchAssignmentOperation {
    Uni<MatchmakerMatchAssignmentModel> execute(SqlConnection sqlConnection,
                                                int shard,
                                                Long matchmakerId,
                                                Long id);
}
