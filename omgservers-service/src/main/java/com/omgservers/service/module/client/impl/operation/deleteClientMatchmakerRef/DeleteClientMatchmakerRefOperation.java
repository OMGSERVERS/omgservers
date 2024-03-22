package com.omgservers.service.module.client.impl.operation.deleteClientMatchmakerRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientMatchmakerRefOperation {
    Uni<Boolean> deleteClientMatchmakerRef(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long clientId,
                                           Long id);
}
