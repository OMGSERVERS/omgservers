package com.omgservers.service.module.runtime.impl.operation.updateRuntimeClientLastActivity;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpdateRuntimeClientLastActivityOperation {
    Uni<Boolean> updateRuntimeClientLastActivity(ChangeContext<?> changeContext,
                                                 SqlConnection sqlConnection,
                                                 int shard,
                                                 final Long runtimeId,
                                                 final Long clientId);
}
