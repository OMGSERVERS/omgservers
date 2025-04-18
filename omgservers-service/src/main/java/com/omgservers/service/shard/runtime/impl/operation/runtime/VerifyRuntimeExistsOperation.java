package com.omgservers.service.shard.runtime.impl.operation.runtime;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyRuntimeExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long id);
}
