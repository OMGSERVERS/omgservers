package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyMatchmakerMatchResourceExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long matchmakerId,
                         Long matchId);
}
