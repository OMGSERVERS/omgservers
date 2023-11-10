package com.omgservers.service.module.matchmaker.impl.operation.selectActiveRequestsByMatchmakerId;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRequestsByMatchmakerIdOperation {
    Uni<List<RequestModel>> selectActiveRequestsByMatchmakerId(SqlConnection sqlConnection,
                                                               int shard,
                                                               Long matchmakerId);
}
