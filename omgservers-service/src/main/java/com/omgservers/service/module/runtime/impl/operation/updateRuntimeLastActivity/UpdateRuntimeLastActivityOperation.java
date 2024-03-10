package com.omgservers.service.module.runtime.impl.operation.updateRuntimeLastActivity;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateRuntimeLastActivityOperation {
    Uni<Boolean> updateRuntimeLastActivity(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           final Long runtimeId);
}