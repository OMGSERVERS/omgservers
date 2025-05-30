package com.omgservers.service.shard.client.impl.operation.client;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int slot,
                              ClientModel client);
}
