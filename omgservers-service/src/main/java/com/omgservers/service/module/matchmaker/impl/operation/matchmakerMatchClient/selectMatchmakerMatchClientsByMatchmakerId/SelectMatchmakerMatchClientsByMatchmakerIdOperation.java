package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClientsByMatchmakerId;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchmakerMatchClientsByMatchmakerIdOperation {
    Uni<List<MatchmakerMatchClientModel>> selectMatchmakerMatchClientsByMatchmakerId(SqlConnection sqlConnection,
                                                                                     int shard,
                                                                                     Long matchmakerId);
}
