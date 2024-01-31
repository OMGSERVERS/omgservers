package com.omgservers.service.module.client.impl.operation.upsertClientMessage;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientMessageOperation {
    Uni<Boolean> upsertClientMessage(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     ClientMessageModel clientMessage);
}
