package com.omgservers.service.module.matchmaker.impl.operation.matchmaker.selectMatchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectMatchmakerOperation {
    Uni<MatchmakerModel> selectMatchmaker(SqlConnection sqlConnection,
                                          int shard,
                                          Long id);
}
