package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.selectMatchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerMatchOperation {
    Uni<MatchmakerMatchModel> selectMatchmakerMatch(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long matchmakerId,
                                                    Long id);
}
