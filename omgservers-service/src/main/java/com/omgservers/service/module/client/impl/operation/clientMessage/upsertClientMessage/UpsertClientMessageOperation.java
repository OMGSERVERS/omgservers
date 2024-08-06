package com.omgservers.service.module.client.impl.operation.clientMessage.upsertClientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientMessageOperation {
    Uni<Boolean> upsertClientMessage(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     ClientMessageModel clientMessage);
}
