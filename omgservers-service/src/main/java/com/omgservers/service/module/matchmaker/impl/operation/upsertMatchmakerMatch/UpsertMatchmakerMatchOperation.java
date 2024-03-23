package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatch;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerMatchOperation {
    Uni<Boolean> upsertMatchmakerMatch(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       MatchmakerMatchModel matchmakerMatch);
}
