package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.deleteMatchmakerMatchClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerMatchClientOperation {
    Uni<Boolean> deleteMatchmakerMatchClient(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             Long matchmakerId,
                                             Long id);
}
