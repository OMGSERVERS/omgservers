package com.omgservers.service.shard.match.impl.operation.match;

import com.omgservers.schema.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchOperation {
    Uni<MatchModel> execute(SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
