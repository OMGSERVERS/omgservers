package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.updateMatchmakerMatchStatus;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateMatchmakerMatchStatusOperation {
    Uni<Boolean> updateMatchmakerMatchStatus(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             Long matchmakerId,
                                             Long matchId,
                                             MatchmakerMatchStatusEnum status);
}
