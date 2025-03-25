package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerRequestOperation {
    Uni<MatchmakerRequestModel> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long matchmakerId,
                                        Long id);
}
