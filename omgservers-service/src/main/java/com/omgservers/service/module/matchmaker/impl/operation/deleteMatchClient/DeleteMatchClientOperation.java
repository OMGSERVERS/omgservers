package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchClientOperation {
    Uni<Boolean> deleteMatchClient(ChangeContext<?> changeContext,
                                   SqlConnection sqlConnection,
                                   int shard,
                                   Long matchmakerId,
                                   Long id);
}
