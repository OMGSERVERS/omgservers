package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface DeleteRuntimeMessagesByIdsOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long runtimeId,
                         List<Long> ids);
}
