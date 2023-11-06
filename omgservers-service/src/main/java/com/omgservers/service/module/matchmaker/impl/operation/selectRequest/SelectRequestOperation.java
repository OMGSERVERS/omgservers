package com.omgservers.service.module.matchmaker.impl.operation.selectRequest;

import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRequestOperation {
    Uni<RequestModel> selectRequest(SqlConnection sqlConnection,
                                    int shard,
                                    Long matchmakerId,
                                    Long id,
                                    Boolean deleted);
}
