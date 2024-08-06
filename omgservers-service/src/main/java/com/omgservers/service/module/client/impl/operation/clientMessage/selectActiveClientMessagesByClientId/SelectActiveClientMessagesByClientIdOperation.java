package com.omgservers.service.module.client.impl.operation.clientMessage.selectActiveClientMessagesByClientId;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveClientMessagesByClientIdOperation {
    Uni<List<ClientMessageModel>> selectActiveClientMessagesByClientId(SqlConnection sqlConnection,
                                                                       int shard,
                                                                       Long clientId);
}
