package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchRuntimeRef;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchRuntimeRefOperation {
    Uni<MatchmakerMatchRuntimeRefModel> execute(SqlConnection sqlConnection,
                                                int shard,
                                                Long matchmakerId,
                                                Long matchId,
                                                Long id);
}
