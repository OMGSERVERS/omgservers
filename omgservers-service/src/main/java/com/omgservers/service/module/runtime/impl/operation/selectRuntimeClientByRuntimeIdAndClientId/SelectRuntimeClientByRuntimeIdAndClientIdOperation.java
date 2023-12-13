package com.omgservers.service.module.runtime.impl.operation.selectRuntimeClientByRuntimeIdAndClientId;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeClientByRuntimeIdAndClientIdOperation {
    Uni<RuntimeClientModel> selectRuntimeClientByRuntimeIdAndEntityId(SqlConnection sqlConnection,
                                                                      int shard,
                                                                      Long runtimeId,
                                                                      Long clientId);
}
