package com.omgservers.service.shard.pool.impl.operation.poolCommand;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolCommandOperation {
    Uni<PoolCommandModel> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long poolId,
                                  Long id);
}
