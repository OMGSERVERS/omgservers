package com.omgservers.module.matchmaker.impl.operation.deleteMatchCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteMatchCommandOperation {
    Uni<Boolean> deleteMatchCommand(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    Long matchmakerId,
                                    Long id);
}
