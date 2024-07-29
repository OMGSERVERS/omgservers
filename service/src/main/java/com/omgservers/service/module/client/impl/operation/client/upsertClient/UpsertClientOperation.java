package com.omgservers.service.module.client.impl.operation.client.upsertClient;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              ClientModel client);
}
