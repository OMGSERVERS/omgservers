package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientMessagesByClientIdOperation {
    Uni<List<ClientMessageModel>> selectActiveClientMessagesByClientId(SqlConnection sqlConnection,
                                                                       int slot,
                                                                       Long clientId);
}
