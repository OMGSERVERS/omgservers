package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatch;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerMatchOperation {
    Uni<Boolean> deleteMatchmakerMatch(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long matchmakerId,
                                       Long id);
}
