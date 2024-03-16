package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerMatchCommandsByMatchId;

import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchCommandsByMatchIdOperation {
    Uni<List<MatchmakerMatchCommandModel>> selectActiveMatchmakerMatchCommandsByMatchId(SqlConnection sqlConnection,
                                                                                        int shard,
                                                                                        Long matchmakerId,
                                                                                        Long matchId);
}
