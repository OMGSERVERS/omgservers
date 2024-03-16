package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchesByMatchmakerId;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchesByMatchmakerIdOperation {
    Uni<List<MatchmakerMatchModel>> selectActiveMatchmakerMatchesByMatchmakerId(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long matchmakerId);
}
