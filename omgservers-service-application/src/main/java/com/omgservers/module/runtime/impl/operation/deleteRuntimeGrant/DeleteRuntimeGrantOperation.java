package com.omgservers.module.runtime.impl.operation.deleteRuntimeGrant;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeGrantOperation {
    Uni<Boolean> deleteRuntimeGrant(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    Long runtimeId,
                                    Long id);
}
