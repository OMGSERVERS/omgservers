package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveMatchmakerRequestsByMatchmakerIdOperation {
    Uni<List<MatchmakerRequestModel>> execute(SqlConnection sqlConnection,
                                              int slot,
                                              Long matchmakerId);
}
