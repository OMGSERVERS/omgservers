package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClientByMatchmakerIdAndClientId;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation {
    Uni<MatchmakerMatchClientModel> selectMatchmakerMatchClientByMatchmakerIdAndClientId(SqlConnection sqlConnection,
                                                                                         int shard,
                                                                                         Long matchmakerId,
                                                                                         Long clientId);
}
