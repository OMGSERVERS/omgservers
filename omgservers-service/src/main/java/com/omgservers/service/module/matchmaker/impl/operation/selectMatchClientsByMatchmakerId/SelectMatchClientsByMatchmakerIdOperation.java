package com.omgservers.service.module.matchmaker.impl.operation.selectMatchClientsByMatchmakerId;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectMatchClientsByMatchmakerIdOperation {
    Uni<List<MatchClientModel>> selectMatchClientsByMatchmakerId(SqlConnection sqlConnection,
                                                                 int shard,
                                                                 Long matchmakerId);
}
