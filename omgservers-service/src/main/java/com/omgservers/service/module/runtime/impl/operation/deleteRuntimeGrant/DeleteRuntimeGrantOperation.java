package com.omgservers.service.module.runtime.impl.operation.deleteRuntimeGrant;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRuntimeGrantOperation {
    Uni<Boolean> deleteRuntimeGrant(ChangeContext<?> changeContext,
                                    SqlConnection sqlConnection,
                                    int shard,
                                    Long runtimeId,
                                    Long id);
}
