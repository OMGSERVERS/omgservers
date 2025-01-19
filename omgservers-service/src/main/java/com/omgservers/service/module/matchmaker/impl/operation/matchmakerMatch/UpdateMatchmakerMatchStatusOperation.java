package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateMatchmakerMatchStatusOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         Long matchmakerId,
                         Long matchId,
                         MatchmakerMatchStatusEnum status);
}
