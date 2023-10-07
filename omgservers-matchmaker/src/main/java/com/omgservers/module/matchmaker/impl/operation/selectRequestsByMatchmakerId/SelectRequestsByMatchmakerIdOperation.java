package com.omgservers.module.matchmaker.impl.operation.selectRequestsByMatchmakerId;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectRequestsByMatchmakerIdOperation {
    Uni<List<RequestModel>> selectRequestsByMatchmakerId(SqlConnection sqlConnection, int shard, Long matchmakerId);
}
