package com.omgservers.service.shard.runtime.impl.operation.runtime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeOperation {
    Uni<RuntimeModel> execute(SqlConnection sqlConnection,
                              int slot,
                              Long id);
}
