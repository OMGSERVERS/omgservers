package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.upsertMatchmakerAssignment;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerAssignmentOperation {
    Uni<Boolean> upsertMatchmakerAssignment(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            int shard,
                                            MatchmakerAssignmentModel matchmakerAssignment);
}
