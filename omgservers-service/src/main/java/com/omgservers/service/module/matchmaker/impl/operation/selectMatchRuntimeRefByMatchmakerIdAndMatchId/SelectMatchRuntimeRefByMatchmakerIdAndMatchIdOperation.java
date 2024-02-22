package com.omgservers.service.module.matchmaker.impl.operation.selectMatchRuntimeRefByMatchmakerIdAndMatchId;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchRuntimeRefByMatchmakerIdAndMatchIdOperation {
    Uni<MatchRuntimeRefModel> selectMatchRuntimeRefByMatchmakerIdAndMatchId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long matchmakerId,
                                                                            Long matchId);
}
