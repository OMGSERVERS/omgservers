package com.omgservers.service.module.client.impl.operation.upsertClientRuntime;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientRuntimeOperation {
    Uni<Boolean> upsertClientRuntime(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     ClientRuntimeModel clientRuntime);
}
