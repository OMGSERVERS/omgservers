package com.omgservers.service.module.runtime.impl.operation.runtimeCommand.upsertRuntimeCommand;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRuntimeCommandOperation {
    Uni<Boolean> upsertRuntimeCommand(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      RuntimeCommandModel runtimeCommand);
}
