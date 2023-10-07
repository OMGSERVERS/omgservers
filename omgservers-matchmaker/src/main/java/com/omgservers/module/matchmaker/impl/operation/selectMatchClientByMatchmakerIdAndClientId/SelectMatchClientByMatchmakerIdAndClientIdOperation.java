package com.omgservers.module.matchmaker.impl.operation.selectMatchClientByMatchmakerIdAndClientId;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchClientByMatchmakerIdAndClientIdOperation {
    Uni<MatchClientModel> selectMatchClientByMatchmakerIdAndClientId(SqlConnection sqlConnection,
                                                                     int shard,
                                                                     Long matchmakerId,
                                                                     Long clientId);
}
