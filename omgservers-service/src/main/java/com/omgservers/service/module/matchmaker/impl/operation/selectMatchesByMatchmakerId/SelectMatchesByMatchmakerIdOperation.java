package com.omgservers.service.module.matchmaker.impl.operation.selectMatchesByMatchmakerId;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchesByMatchmakerIdOperation {
    Uni<List<MatchModel>> selectMatchesByMatchmakerId(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long matchmakerId);
}
