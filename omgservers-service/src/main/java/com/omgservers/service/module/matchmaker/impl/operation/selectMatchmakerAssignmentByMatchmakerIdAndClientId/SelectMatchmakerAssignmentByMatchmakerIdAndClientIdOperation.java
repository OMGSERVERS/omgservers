package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerAssignmentByMatchmakerIdAndClientId;

import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerAssignmentByMatchmakerIdAndClientIdOperation {
    Uni<MatchmakerAssignmentModel> selectMatchmakerAssignmentByMatchmakerIdAndClientId(SqlConnection sqlConnection,
                                                                                       int shard,
                                                                                       Long matchmakerId,
                                                                                       Long clientId);
}
