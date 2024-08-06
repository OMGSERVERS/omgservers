package com.omgservers.service.module.runtime.impl.operation.runtime.updateRuntimeLastActivity;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateRuntimeLastActivityOperation {
    Uni<Boolean> updateRuntimeLastActivity(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long runtimeId);
}
