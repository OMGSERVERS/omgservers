package com.omgservers.service.module.client.impl.operation.upsertClientRuntimeRef;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientRuntimeRefOperation {
    Uni<Boolean> upsertClientRuntimeRef(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        ClientRuntimeRefModel clientRuntimeRef);
}
