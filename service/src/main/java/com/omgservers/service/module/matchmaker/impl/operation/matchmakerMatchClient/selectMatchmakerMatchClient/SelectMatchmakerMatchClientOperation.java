package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClient;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchClientOperation {
    Uni<MatchmakerMatchClientModel> selectMatchmakerMatchClient(SqlConnection sqlConnection,
                                                                int shard,
                                                                Long matchmakerId,
                                                                Long id);
}
