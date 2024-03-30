package com.omgservers.service.module.client.impl.operation.client.deleteClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientOperation {
    Uni<Boolean> deleteClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
