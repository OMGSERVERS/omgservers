package com.omgservers.module.matchmaker.impl.operation.updateMatch;

import com.omgservers.model.match.MatchModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateMatchOperation {
    Uni<Boolean> updateMatch(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             MatchModel match);
}
