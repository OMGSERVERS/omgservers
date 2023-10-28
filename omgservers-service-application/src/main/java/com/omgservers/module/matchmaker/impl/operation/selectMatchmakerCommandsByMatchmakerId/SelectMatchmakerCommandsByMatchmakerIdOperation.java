package com.omgservers.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerId;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchmakerCommandsByMatchmakerIdOperation {
    Uni<List<MatchmakerCommandModel>> selectMatchmakerCommandsByMatchmakerIdAndMatchId(SqlConnection sqlConnection,
                                                                                       int shard,
                                                                                       Long matchmakerId);
}
