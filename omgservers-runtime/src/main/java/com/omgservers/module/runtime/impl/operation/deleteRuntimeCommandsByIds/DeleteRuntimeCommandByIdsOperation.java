package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommandsByIds;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface DeleteRuntimeCommandByIdsOperation {
    Uni<Boolean> deleteRuntimeCommandByIds(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long runtimeId,
                                           List<Long> ids);
}
