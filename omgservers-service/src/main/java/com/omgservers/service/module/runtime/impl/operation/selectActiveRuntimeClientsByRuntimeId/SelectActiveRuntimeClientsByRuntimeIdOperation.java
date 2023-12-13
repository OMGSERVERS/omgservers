package com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeClientsByRuntimeId;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeClientsByRuntimeIdOperation {
    Uni<List<RuntimeClientModel>> selectActiveRuntimeClientsByRuntimeId(SqlConnection sqlConnection,
                                                                        int shard,
                                                                        Long runtimeId);
}
