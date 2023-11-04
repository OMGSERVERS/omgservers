package com.omgservers.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerIdAndMatchId;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchClientsByMatchmakerIdAndMatchIdOperation {
    Uni<List<MatchClientModel>> selectMatchClientsByMatchmakerIdAndMatchId(SqlConnection sqlConnection,
                                                                           int shard,
                                                                           Long matchmakerId,
                                                                           Long matchId);
}
