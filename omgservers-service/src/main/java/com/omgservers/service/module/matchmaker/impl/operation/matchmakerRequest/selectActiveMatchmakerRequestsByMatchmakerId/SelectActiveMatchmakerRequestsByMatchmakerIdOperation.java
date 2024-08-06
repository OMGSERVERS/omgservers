package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectActiveMatchmakerRequestsByMatchmakerId;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerRequestsByMatchmakerIdOperation {
    Uni<List<MatchmakerRequestModel>> selectActiveMatchmakerRequestsByMatchmakerId(SqlConnection sqlConnection,
                                                                                   int shard,
                                                                                   Long matchmakerId);
}
