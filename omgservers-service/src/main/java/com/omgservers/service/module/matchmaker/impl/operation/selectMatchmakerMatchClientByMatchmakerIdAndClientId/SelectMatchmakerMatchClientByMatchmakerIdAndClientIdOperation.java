package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerMatchClientByMatchmakerIdAndClientId;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation {
    Uni<MatchmakerMatchClientModel> selectMatchmakerMatchClientByMatchmakerIdAndClientId(SqlConnection sqlConnection,
                                                                                         int shard,
                                                                                         Long matchmakerId,
                                                                                         Long clientId);
}
