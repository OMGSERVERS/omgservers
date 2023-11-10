package com.omgservers.service.module.matchmaker.impl.operation.selectMatch;

import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchOperation {
    Uni<MatchModel> selectMatch(SqlConnection sqlConnection,
                                int shard,
                                Long matchmakerId,
                                Long id);
}
