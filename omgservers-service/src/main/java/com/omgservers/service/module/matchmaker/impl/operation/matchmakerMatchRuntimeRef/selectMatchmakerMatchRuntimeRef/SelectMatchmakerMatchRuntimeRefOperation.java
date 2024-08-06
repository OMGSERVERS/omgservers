package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.selectMatchmakerMatchRuntimeRef;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchRuntimeRefOperation {
    Uni<MatchmakerMatchRuntimeRefModel> selectMatchmakerMatchRuntimeRef(SqlConnection sqlConnection,
                                                                        int shard,
                                                                        Long matchmakerId,
                                                                        Long matchId,
                                                                        Long id);
}
