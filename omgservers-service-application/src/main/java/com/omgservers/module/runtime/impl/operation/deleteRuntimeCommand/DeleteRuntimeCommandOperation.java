package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeCommandOperation {
    Uni<Boolean> deleteRuntimeCommand(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      Long runtimeId,
                                      Long id);
}
