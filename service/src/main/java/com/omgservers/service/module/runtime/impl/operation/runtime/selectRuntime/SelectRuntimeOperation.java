package com.omgservers.service.module.runtime.impl.operation.runtime.selectRuntime;

import com.omgservers.schema.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimeOperation {
    Uni<RuntimeModel> selectRuntime(SqlConnection sqlConnection, int shard, Long id);
}
