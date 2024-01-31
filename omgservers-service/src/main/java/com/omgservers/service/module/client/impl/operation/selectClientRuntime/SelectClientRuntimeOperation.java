package com.omgservers.service.module.client.impl.operation.selectClientRuntime;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeOperation {
    Uni<ClientRuntimeModel> selectClientRuntime(SqlConnection sqlConnection,
                                                int shard,
                                                Long clientId,
                                                Long id);
}
