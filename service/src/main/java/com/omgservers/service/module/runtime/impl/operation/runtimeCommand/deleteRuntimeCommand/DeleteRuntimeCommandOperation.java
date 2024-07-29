package com.omgservers.service.module.runtime.impl.operation.runtimeCommand.deleteRuntimeCommand;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeCommandOperation {
    Uni<Boolean> deleteRuntimeCommand(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      Long runtimeId,
                                      Long id);
}
