package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectMatchmakerRequest;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerRequestOperation {
    Uni<MatchmakerRequestModel> selectMatchmakerRequest(SqlConnection sqlConnection,
                                                        int shard,
                                                        Long matchmakerId,
                                                        Long id);
}
