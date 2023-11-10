package com.omgservers.service.module.matchmaker.impl.operation.selectActiveMatchmakerCommandsByMatchmakerId;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerCommandsByMatchmakerIdOperation {
    Uni<List<MatchmakerCommandModel>> selectActiveMatchmakerCommandsByMatchmakerId(SqlConnection sqlConnection,
                                                                                   int shard,
                                                                                   Long matchmakerId);
}
