package com.omgservers.service.shard.runtime.impl.operation.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeCommandsByRuntimeIdOperation {
    Uni<List<RuntimeCommandModel>> execute(SqlConnection sqlConnection,
                                           int shard,
                                           Long runtimeId);
}
