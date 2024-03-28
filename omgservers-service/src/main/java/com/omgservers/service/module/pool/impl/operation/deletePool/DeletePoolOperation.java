package com.omgservers.service.module.pool.impl.operation.deletePool;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolOperation {
    Uni<Boolean> deletePool(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
