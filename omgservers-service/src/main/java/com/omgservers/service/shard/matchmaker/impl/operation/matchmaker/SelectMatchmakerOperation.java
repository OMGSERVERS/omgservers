package com.omgservers.service.shard.matchmaker.impl.operation.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerOperation {
    Uni<MatchmakerModel> execute(SqlConnection sqlConnection,
                                 int slot,
                                 Long id);
}
