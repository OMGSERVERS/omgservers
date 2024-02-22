package com.omgservers.service.module.matchmaker.impl.operation.selectMatchRuntimeRef;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchRuntimeRefOperation {
    Uni<MatchRuntimeRefModel> selectMatchRuntimeRef(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long matchmakerId,
                                                    Long matchId,
                                                    Long id);
}
