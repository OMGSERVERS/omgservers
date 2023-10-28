package com.omgservers.module.runtime.impl.operation.selectRuntime;

import com.omgservers.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeOperation {
    Uni<RuntimeModel> selectRuntime(SqlConnection sqlConnection, int shard, Long id, Boolean deleted);
}
