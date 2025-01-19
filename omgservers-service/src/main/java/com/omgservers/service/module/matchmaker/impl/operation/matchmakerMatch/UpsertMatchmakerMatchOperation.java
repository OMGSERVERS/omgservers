package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertMatchmakerMatchOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         MatchmakerMatchModel matchmakerMatch);
}
