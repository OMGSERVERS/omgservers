package com.omgservers.service.module.user.impl.operation.upsertClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientOperation {
    Uni<Boolean> upsertClient(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              ClientModel client);
}
