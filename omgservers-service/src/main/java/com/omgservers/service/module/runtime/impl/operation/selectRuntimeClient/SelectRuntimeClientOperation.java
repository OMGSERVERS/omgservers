package com.omgservers.service.module.runtime.impl.operation.selectRuntimeClient;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeClientOperation {
    Uni<RuntimeClientModel> selectRuntimeClient(SqlConnection sqlConnection,
                                                int shard,
                                                Long runtimeId,
                                                Long id);
}
