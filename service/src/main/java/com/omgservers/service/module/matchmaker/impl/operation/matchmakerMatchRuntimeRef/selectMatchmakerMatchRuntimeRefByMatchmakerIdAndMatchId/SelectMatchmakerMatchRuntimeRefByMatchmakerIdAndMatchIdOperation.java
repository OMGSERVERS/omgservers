package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchIdOperation {
    Uni<MatchmakerMatchRuntimeRefModel> selectMatchmakerMatchRuntimeRefByMatchmakerIdAndMatchId(
            SqlConnection sqlConnection,
            int shard,
            Long matchmakerId,
            Long matchId);
}
