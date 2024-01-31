package com.omgservers.service.module.client.impl.operation.deleteClientRuntime;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientRuntimeOperation {
    Uni<Boolean> deleteClientRuntime(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     Long clientId,
                                     Long id);
}
