package com.omgservers.service.shard.runtime.impl.operation.runtimePoolContainerRef;

import com.omgservers.schema.model.runtimePoolContainerRef.RuntimePoolContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRuntimePoolContainerRefOperation {
    Uni<RuntimePoolContainerRefModel> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long runtimeId,
                                              Long id);
}
