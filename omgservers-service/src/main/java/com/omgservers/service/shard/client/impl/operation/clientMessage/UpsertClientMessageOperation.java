package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientMessageOperation {
    Uni<Boolean> upsertClientMessage(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     ClientMessageModel clientMessage);
}
