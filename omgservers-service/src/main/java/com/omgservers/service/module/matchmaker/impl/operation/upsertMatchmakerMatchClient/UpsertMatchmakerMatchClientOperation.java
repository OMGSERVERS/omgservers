package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchClient;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertMatchmakerMatchClientOperation {
    Uni<Boolean> upsertMatchmakerMatchClient(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             MatchmakerMatchClientModel matchmakerMatchClient);
}
