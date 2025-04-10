package com.omgservers.service.shard.client.impl.operation.clientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeRefOperation {
    Uni<ClientRuntimeRefModel> selectClientRuntimeRef(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long clientId,
                                                      Long id);
}
