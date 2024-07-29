package com.omgservers.service.module.client.impl.operation.clientRuntimeRef.selectClientRuntimeRef;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeRefOperation {
    Uni<ClientRuntimeRefModel> selectClientRuntimeRef(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long clientId,
                                                      Long id);
}
