package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long matchmakerId,
                         Long id);
}
