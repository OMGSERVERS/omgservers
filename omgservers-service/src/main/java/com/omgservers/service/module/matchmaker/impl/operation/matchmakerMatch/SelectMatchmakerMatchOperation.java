package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchOperation {
    Uni<MatchmakerMatchModel> execute(SqlConnection sqlConnection,
                                      int shard,
                                      Long matchmakerId,
                                      Long id);
}
