package com.omgservers.service.module.client.impl.operation.selectClientRuntimeRef;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeRefOperation {
    Uni<ClientRuntimeRefModel> selectClientRuntimeRef(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long clientId,
                                                      Long id);
}
