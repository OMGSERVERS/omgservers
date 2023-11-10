package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchClientsByMatchId;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchClientsByMatchIdOperation {
    Uni<List<MatchClientModel>> selectActiveMatchClientsByMatchId(SqlConnection sqlConnection,
                                                                  int shard,
                                                                  Long matchmakerId,
                                                                  Long matchId);
}
