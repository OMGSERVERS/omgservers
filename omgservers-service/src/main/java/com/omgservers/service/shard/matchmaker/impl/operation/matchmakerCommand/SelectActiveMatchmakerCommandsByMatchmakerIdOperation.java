package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerCommandsByMatchmakerIdOperation {
    Uni<List<MatchmakerCommandModel>> execute(SqlConnection sqlConnection,
                                              int slot,
                                              Long matchmakerId);
}
