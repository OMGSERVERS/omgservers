package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchesByMatchmakerId;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchesByMatchmakerIdOperation {
    Uni<List<MatchModel>> selectActiveMatchesByMatchmakerId(SqlConnection sqlConnection,
                                                            int shard,
                                                            Long matchmakerId);
}
