package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRuntimeMessagesByRuntimeIdOperation {
    Uni<List<RuntimeMessageModel>> execute(SqlConnection sqlConnection,
                                           int slot,
                                           Long runtimeId);
}
