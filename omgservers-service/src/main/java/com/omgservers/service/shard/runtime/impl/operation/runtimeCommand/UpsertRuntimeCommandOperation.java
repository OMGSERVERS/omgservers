package com.omgservers.service.shard.runtime.impl.operation.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeCommandOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         RuntimeCommandModel runtimeCommand);
}
