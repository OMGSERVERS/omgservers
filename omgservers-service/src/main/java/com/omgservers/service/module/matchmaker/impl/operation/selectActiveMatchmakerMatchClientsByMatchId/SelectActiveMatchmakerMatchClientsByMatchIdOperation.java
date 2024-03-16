package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchClientsByMatchId;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchClientsByMatchIdOperation {
    Uni<List<MatchmakerMatchClientModel>> selectActiveMatchmakerMatchClientsByMatchId(SqlConnection sqlConnection,
                                                                                      int shard,
                                                                                      Long matchmakerId,
                                                                                      Long matchId);
}
