package com.omgservers.service.module.client.impl.operation.selectClientRuntimeByClientIdAndRuntimeId;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeByClientIdAndRuntimeIdOperation {
    Uni<ClientRuntimeModel> selectClientRuntimeByClientIdAndRuntimeId(SqlConnection sqlConnection,
                                                                      int shard,
                                                                      Long clientId,
                                                                      Long runtimeId);
}
