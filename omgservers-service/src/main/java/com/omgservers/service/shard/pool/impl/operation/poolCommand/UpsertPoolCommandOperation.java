package com.omgservers.service.shard.pool.impl.operation.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolCommandOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         PoolCommandModel poolCommand);
}
