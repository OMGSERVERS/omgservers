package com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.deleteClientMatchmakerRef;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientMatchmakerRefOperation {
    Uni<Boolean> deleteClientMatchmakerRef(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long clientId,
                                           Long id);
}
