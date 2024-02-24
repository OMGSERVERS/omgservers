package com.omgservers.service.module.client.impl.operation.selectClientRuntimeRefByClientIdAndRuntimeId;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectClientRuntimeRefByClientIdAndRuntimeIdOperation {
    Uni<ClientRuntimeRefModel> selectClientRuntimeRefByClientIdAndRuntimeId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long clientId,
                                                                            Long runtimeId);
}