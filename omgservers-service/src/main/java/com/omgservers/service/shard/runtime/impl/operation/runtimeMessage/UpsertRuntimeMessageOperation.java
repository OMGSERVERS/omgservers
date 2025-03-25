package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeMessageOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         RuntimeMessageModel runtimeMessage);
}
