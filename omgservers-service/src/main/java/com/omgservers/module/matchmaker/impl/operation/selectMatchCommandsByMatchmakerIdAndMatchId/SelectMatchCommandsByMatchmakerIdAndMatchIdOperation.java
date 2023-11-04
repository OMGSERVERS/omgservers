package com.omgservers.module.matchmaker.impl.operation.selectMatchCommandsByMatchmakerIdAndMatchId;

import com.omgservers.model.matchCommand.MatchCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchCommandsByMatchmakerIdAndMatchIdOperation {
    Uni<List<MatchCommandModel>> selectMatchCommandsByMatchmakerIdAndMatchId(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long matchmakerId,
                                                                             Long matchId);
}
