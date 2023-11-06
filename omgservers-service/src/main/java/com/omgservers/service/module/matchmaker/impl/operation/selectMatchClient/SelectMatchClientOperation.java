package com.omgservers.service.module.matchmaker.impl.operation.selectMatchClient;

import com.omgservers.model.matchClient.MatchClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchClientOperation {
    Uni<MatchClientModel> selectMatchClient(SqlConnection sqlConnection,
                                            int shard,
                                            Long matchmakerId,
                                            Long id,
                                            Boolean deleted);
}
