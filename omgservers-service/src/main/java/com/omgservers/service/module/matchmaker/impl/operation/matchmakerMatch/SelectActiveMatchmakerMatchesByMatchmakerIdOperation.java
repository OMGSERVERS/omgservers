package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerMatchesByMatchmakerIdOperation {
    Uni<List<MatchmakerMatchModel>> execute(SqlConnection sqlConnection,
                                            int shard,
                                            Long matchmakerId);
}
