package com.omgservers.service.module.runtime.impl.operation.runtimeCommand.selectActiveRuntimeCommandsByRuntimeId;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeCommandsByRuntimeIdOperation {
    Uni<List<RuntimeCommandModel>> selectActiveRuntimeCommandsByRuntimeId(SqlConnection sqlConnection,
                                                                          int shard,
                                                                          Long runtimeId);
}
