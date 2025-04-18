package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertClientRuntimeRefOperation {
    Uni<Boolean> upsertClientRuntimeRef(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int slot,
                                        ClientRuntimeRefModel clientRuntimeRef);
}
