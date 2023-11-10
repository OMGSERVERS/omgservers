package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchCommandsByMatchId;

import com.omgservers.model.matchCommand.MatchCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchCommandsByMatchIdOperation {
    Uni<List<MatchCommandModel>> selectActiveMatchCommandsByMatchId(SqlConnection sqlConnection,
                                                                    int shard,
                                                                    Long matchmakerId,
                                                                    Long matchId);
}
