package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.deleteMatchmakerRequest;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchmakerRequestOperation {
    Uni<Boolean> deleteMatchmakerRequest(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long matchmakerId,
                                         Long id);
}
