package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface DeleteClientMessagesByIdsOperation {
    Uni<Boolean> deleteClientMessagesByIds(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int slot,
                                           Long clientId,
                                           List<Long> ids);
}
