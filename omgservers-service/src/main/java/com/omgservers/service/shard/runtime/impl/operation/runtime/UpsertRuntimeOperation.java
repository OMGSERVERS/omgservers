package com.omgservers.service.shard.runtime.impl.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         RuntimeModel runtime);
}
