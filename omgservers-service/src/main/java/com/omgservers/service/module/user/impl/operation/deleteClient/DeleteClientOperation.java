package com.omgservers.service.module.user.impl.operation.deleteClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientOperation {
    Uni<Boolean> deleteClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long userId,
                              Long id);
}
